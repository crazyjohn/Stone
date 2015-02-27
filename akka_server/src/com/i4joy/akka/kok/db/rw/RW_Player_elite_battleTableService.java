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
import com.ump.impl.JdbcPlayer_elite_battleDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_elite_battle;

public class RW_Player_elite_battleTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_elite_battleTableService.class, dataSource, DBName);
	}

	private static HashMap<Integer, Player_elite_battle> hmCache = new HashMap<Integer, Player_elite_battle>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "player_elite_battle";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_elite_battleTableService(DataSource dataSource, String DBName) {
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
		if (msg instanceof Player_elite_battle) {
			Player_elite_battle pg = (Player_elite_battle) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				long curTime = System.currentTimeMillis();
				Iterator<Map.Entry<Integer, Player_elite_battle>> iterator = hmCache.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, Player_elite_battle> entry = iterator.next();
					Player_elite_battle state = entry.getValue();
					if (state.getCacheTime() - curTime > Property.MINUTE) {
						iterator.remove();
					}
				}

			}
		}
	}

	public static void addCache(Player_elite_battle pg) {
		pg.setCacheTime(System.currentTimeMillis());
		hmCache.put(pg.getPlayer_id(), pg);
	}

	public static class PlayerGoldSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Integer) {// 收到请求角色消息
				Integer id = (Integer) msg;
				Player_elite_battle pg = hmCache.get(id);
				if (pg == null) {
					pg = JdbcPlayer_elite_battleDAO.getInstance().getPlayer_elite_battle(id.intValue());
					if (pg == null) {
						pg = new Player_elite_battle();
					} else {
						addCache(pg);
					}
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class PlayerGoldInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_elite_battle> hmInsert = new HashMap<Integer, Player_elite_battle>();
		private HashMap<Integer, Player_elite_battle> hmUpDate = new HashMap<Integer, Player_elite_battle>();
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
			if (msg instanceof Player_elite_battle) {// 收到请求角色消息
				Player_elite_battle pg = (Player_elite_battle) msg;
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
					Player_elite_battle[] pgs = new Player_elite_battle[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_elite_battleDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_elite_battle[] pgs = new Player_elite_battle[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_elite_battleDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
