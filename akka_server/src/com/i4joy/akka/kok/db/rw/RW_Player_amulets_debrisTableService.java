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
import com.ump.impl.JdbcPlayer_amulets_debrisDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_amulets_debris;

public class RW_Player_amulets_debrisTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_amulets_debrisTableService.class, dataSource, DBName);
	}

	// private static HashMap<Integer, Player_amulets_debris> hmCache = new
	// HashMap<Integer, Player_amulets_debris>();
	private static LoadingCache<Integer, Optional<Player_amulets_debris>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "player_amulets_debris";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_amulets_debrisTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_amulets_debris>>() {
			@Override
			public Optional<Player_amulets_debris> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_amulets_debrisDAO.getInstance().getEquipment_debris(key));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerAmuletsDebrisSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerAmuletsDebrisInsertUpDateWorker.class));
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
		if (msg instanceof Player_amulets_debris) {
			Player_amulets_debris pg = (Player_amulets_debris) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_amulets_debris pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class PlayerAmuletsDebrisSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_amulets_debris) {// 收到请求角色消息
				Player_amulets_debris pad = (Player_amulets_debris) msg;
				Optional<Player_amulets_debris> op = hmCache.get(pad.getPlayer_id());
				if (op.isPresent()) {
					pad = op.get();
				} else {
					pad.setAction(DBCache.FAIL);
				}
				getSender().tell(pad, getSelf());
			}
		}
	}

	public static class PlayerAmuletsDebrisInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_amulets_debris> hmInsert = new HashMap<Integer, Player_amulets_debris>();
		private HashMap<Integer, Player_amulets_debris> hmUpDate = new HashMap<Integer, Player_amulets_debris>();
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
			if (msg instanceof Player_amulets_debris) {// 收到请求角色消息
				Player_amulets_debris pg = (Player_amulets_debris) msg;
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
					Player_amulets_debris[] pgs = new Player_amulets_debris[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_amulets_debrisDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_amulets_debris[] pgs = new Player_amulets_debris[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_amulets_debrisDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
