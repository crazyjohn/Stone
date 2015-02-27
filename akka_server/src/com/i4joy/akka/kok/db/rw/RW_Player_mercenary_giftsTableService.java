package com.i4joy.akka.kok.db.rw;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcPlayer_mercenary_giftsDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_mercenary_gifts;

public class RW_Player_mercenary_giftsTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_mercenary_giftsTableService.class, dataSource, DBName);
	}

	// private static HashMap<Integer, Player_mercenary_gifts> hmCache = new
	// HashMap<Integer, Player_mercenary_gifts>();
	private static LoadingCache<Integer, Optional<Player_mercenary_gifts>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	// private String tableName = "player_mercenary_gifts";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	// private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_mercenary_giftsTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_mercenary_gifts>>() {
			@Override
			public Optional<Player_mercenary_gifts> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_mercenary_giftsDAO.getInstance().getPlayerMercenaryGift(key));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerMercenaryGiftsSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerMercenaryGiftsInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + Player_mercenary_gifts.tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + Player_mercenary_gifts.tableName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Player_mercenary_gifts) {
			Player_mercenary_gifts pg = (Player_mercenary_gifts) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_mercenary_gifts pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class PlayerMercenaryGiftsSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_mercenary_gifts) {// 收到请求角色消息
				Player_mercenary_gifts pmg = (Player_mercenary_gifts) msg;
				Optional<Player_mercenary_gifts> pg = hmCache.get(pmg.getPlayer_id());
				if (pg.isPresent()) {
					pmg = pg.get();
				} else {
					pmg.setAction(DBCache.FAIL);
				}
				getSender().tell(pmg, getSelf());
			}
		}
	}

	public static class PlayerMercenaryGiftsInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_mercenary_gifts> hmInsert = new HashMap<Integer, Player_mercenary_gifts>();
		private HashMap<Integer, Player_mercenary_gifts> hmUpDate = new HashMap<Integer, Player_mercenary_gifts>();
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
			if (msg instanceof Player_mercenary_gifts) {// 收到请求角色消息
				Player_mercenary_gifts pg = (Player_mercenary_gifts) msg;
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
					Player_mercenary_gifts[] pgs = new Player_mercenary_gifts[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_mercenary_giftsDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_mercenary_gifts[] pgs = new Player_mercenary_gifts[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_mercenary_giftsDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
