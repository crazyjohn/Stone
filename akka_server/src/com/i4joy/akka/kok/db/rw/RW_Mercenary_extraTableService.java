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
import com.ump.impl.JdbcMercenary_extraDAO;
import com.ump.model.DBCache;
import com.ump.model.Mercenary_extra;

public class RW_Mercenary_extraTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Mercenary_extraTableService.class, dataSource, DBName);
	}

	private static HashMap<Integer, Mercenary_extra> hmCache = new HashMap<Integer, Mercenary_extra>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "mercenary_extra";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Mercenary_extraTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerGoldSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)), routerName);
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerGoldInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + "#" + tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + "#" + tableName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Mercenary_extra) {
			Mercenary_extra pg = (Mercenary_extra) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				long curTime = System.currentTimeMillis();
				Iterator<Map.Entry<Integer, Mercenary_extra>> iterator = hmCache.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, Mercenary_extra> entry = iterator.next();
					Mercenary_extra state = entry.getValue();
					if (state.getCacheTime() - curTime > Property.MINUTE) {
						iterator.remove();
					}
				}

			}
		}
	}

	public static void addCache(Mercenary_extra pg) {
		pg.setCacheTime(System.currentTimeMillis());
		hmCache.put(pg.getExtra_id(), pg);
	}

	public static class PlayerGoldSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Integer) {// 收到请求角色消息
				Integer id = (Integer) msg;
				Mercenary_extra pg = hmCache.get(id);
				if (pg == null) {
					pg = JdbcMercenary_extraDAO.getInstance().getMercenary_extra(id.intValue());
					if (pg == null) {
						pg = new Mercenary_extra();
					} else {
						addCache(pg);
					}
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class PlayerGoldInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Mercenary_extra> hmInsert = new HashMap<Integer, Mercenary_extra>();
		private HashMap<Integer, Mercenary_extra> hmUpDate = new HashMap<Integer, Mercenary_extra>();
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
			if (msg instanceof Mercenary_extra) {// 收到请求角色消息
				Mercenary_extra pg = (Mercenary_extra) msg;
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
					Mercenary_extra[] pgs = new Mercenary_extra[len];
					hmInsert.values().toArray(pgs);
					JdbcMercenary_extraDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Mercenary_extra[] pgs = new Mercenary_extra[len];
					hmUpDate.values().toArray(pgs);
					JdbcMercenary_extraDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
