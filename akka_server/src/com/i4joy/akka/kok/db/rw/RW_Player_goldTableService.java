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
import com.ump.impl.JdbcPlayer_goldDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_gold;

public class RW_Player_goldTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_goldTableService.class, dataSource, DBName);
	}

	private static HashMap<Integer, Player_gold> hmCache = new HashMap<Integer, Player_gold>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "player_gold";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_goldTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerGoldSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
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
		if (msg instanceof Player_gold) {
			Player_gold pg = (Player_gold) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				long curTime = System.currentTimeMillis();
				Iterator<Map.Entry<Integer, Player_gold>> iterator = hmCache.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, Player_gold> entry = iterator.next();
					Player_gold state = entry.getValue();
					if (state.getCacheTime() - curTime > Property.MINUTE) {
						iterator.remove();
					}
				}

			}
		}
	}

	public static void addCache(Player_gold pg) {
		pg.setCacheTime(System.currentTimeMillis());
		hmCache.put(pg.getPlayer_id(), pg);
	}

	public static class PlayerGoldSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Integer) {// 收到请求角色消息
				Integer playerId = (Integer) msg;
				Player_gold pg = hmCache.get(playerId);
				if (pg == null) {
					pg = JdbcPlayer_goldDAO.getInstance().getPlayerGold(playerId.intValue());
					if (pg == null) {
						pg = new Player_gold();
					} else {
						addCache(pg);
					}
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class PlayerGoldInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_gold> hmInsert = new HashMap<Integer, Player_gold>();
		private HashMap<Integer, Player_gold> hmUpDate = new HashMap<Integer, Player_gold>();
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
			if (msg instanceof Player_gold) {// 收到请求角色消息
				Player_gold pg = (Player_gold) msg;
				if (pg.getAction() == DBCache.INSERT) {
					addCache(pg);
					hmInsert.put(pg.getPlayer_id(), pg);
				} else if (pg.getAction() == DBCache.UPDATE) {
					addCache(pg);
					hmUpDate.put(pg.getPlayer_id(), pg);
				}

			} else if (msg instanceof Heart) {
				int len = hmInsert.size();
				if (len > 0) {
					Player_gold[] pgs = new Player_gold[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_goldDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_gold[] pgs = new Player_gold[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_goldDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
