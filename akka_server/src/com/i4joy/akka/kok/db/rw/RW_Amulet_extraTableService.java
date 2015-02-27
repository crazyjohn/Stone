package com.i4joy.akka.kok.db.rw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcAmulet_extraDAO;
import com.ump.model.Amulet_extra;
import com.ump.model.DBCache;

public class RW_Amulet_extraTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Amulet_extraTableService.class, dataSource, DBName);
	}

	private static HashMap<Integer, Amulet_extra> hmCache = new HashMap<Integer, Amulet_extra>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	public static String topic = "amulet_extra";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Amulet_extraTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerGoldSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerGoldInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + "#" + topic, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + "#" + topic, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Amulet_extra) {
			Amulet_extra pg = (Amulet_extra) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				long curTime = System.currentTimeMillis();
				Iterator<Map.Entry<Integer, Amulet_extra>> iterator = hmCache.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, Amulet_extra> entry = iterator.next();
					Amulet_extra state = entry.getValue();
					if (state.getCacheTime() - curTime > Property.MINUTE) {
						iterator.remove();
					}
				}

			}
		}
	}

	public static void addCache(Amulet_extra pg) {
		pg.setCacheTime(System.currentTimeMillis());
		hmCache.put(pg.getExtra_id(), pg);
	}

	public static class PlayerGoldSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Integer) {// 收到请求角色消息
				Integer id = (Integer) msg;
				Amulet_extra pg = hmCache.get(id);
				if (pg == null) {
					pg = JdbcAmulet_extraDAO.getInstance().getAmulet_extra(id.intValue());
					if (pg == null) {
						pg = new Amulet_extra();
					} else {
						addCache(pg);
					}
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class PlayerGoldInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Amulet_extra> hmInsert = new HashMap<Integer, Amulet_extra>();
		private HashMap<Integer, Amulet_extra> hmUpDate = new HashMap<Integer, Amulet_extra>();
		private Cancellable heart;// 心跳 清理缓存中超时的

		@Override
		public void preStart() throws Exception {
			getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
			super.preStart();
		}

		@Override
		public void postStop() throws Exception {
			heart.cancel();
			super.postStop();
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Amulet_extra) {// 收到请求角色消息
				Amulet_extra pg = (Amulet_extra) msg;
				if (pg.getAction() == DBCache.INSERT) {
					addCache(pg);
					hmInsert.put(pg.getExtra_id(), pg);
				} else if (pg.getAction() == DBCache.UPDATE) {
					addCache(pg);
					hmUpDate.put(pg.getExtra_id(), pg);
				}

			} else if (msg instanceof Heart) {
				int len = hmInsert.size();
				if (len > 0) {
					Amulet_extra[] pgs = new Amulet_extra[len];
					hmInsert.values().toArray(pgs);
					JdbcAmulet_extraDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Amulet_extra[] pgs = new Amulet_extra[len];
					hmUpDate.values().toArray(pgs);
					JdbcAmulet_extraDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
