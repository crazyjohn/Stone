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
import com.ump.impl.JdbcPlayer_amuletsDAO;
import com.ump.impl.JdbcPlayer_mercenary_debirsDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_amulets;
import com.ump.model.Player_mercenary_debirs;

public class RW_Player_amuletsTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_amuletsTableService.class, dataSource, DBName);
	}

	// private static HashMap<Integer, Player_amulets> hmCache = new
	// HashMap<Integer, Player_amulets>();
	private static LoadingCache<Integer, Optional<Player_amulets>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	// private String tableName = "player_amulets";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	// private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_amuletsTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_amulets>>() {
			@Override
			public Optional<Player_amulets> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_amuletsDAO.getInstance().getPlayer_amulets(key));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerAmuletsSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerAmuletsInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + Player_amulets.tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + Player_amulets.tableName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Player_amulets) {
			Player_amulets pg = (Player_amulets) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_amulets pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class PlayerAmuletsSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_amulets) {// 收到请求角色消息
				Player_amulets pa = (Player_amulets) msg;
				Optional<Player_amulets> pg = hmCache.get(pa.getPlayer_id());
				if (pg.isPresent()) {
					pa = pg.get();
				} else {
					pa.setAction(DBCache.FAIL);
				}
				getSender().tell(pa, getSelf());
			}
		}
	}

	public static class PlayerAmuletsInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_amulets> hmInsert = new HashMap<Integer, Player_amulets>();
		private HashMap<Integer, Player_amulets> hmUpDate = new HashMap<Integer, Player_amulets>();
		private Cancellable heart;// 心跳 清理缓存中超时的

		@Override
		public void preStart() throws Exception {
			heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
			super.preStart();
		}

		@Override
		public void postStop() throws Exception {
			heart.cancel();
			super.postStop();
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_amulets) {// 收到请求角色消息
				Player_amulets pg = (Player_amulets) msg;
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
					Player_amulets[] pgs = new Player_amulets[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_amuletsDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_amulets[] pgs = new Player_amulets[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_amuletsDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
