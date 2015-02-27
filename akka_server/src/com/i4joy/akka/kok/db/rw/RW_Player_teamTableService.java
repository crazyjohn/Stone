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
import com.ump.impl.JdbcPlayer_teamDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_team;

public class RW_Player_teamTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_teamTableService.class, dataSource, DBName);
	}

	private static LoadingCache<Integer, Optional<Player_team>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "player_team";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_teamTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_team>>() {
			@Override
			public Optional<Player_team> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_teamDAO.getInstance().getPlayer_team(key));
			}
		});
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
		if (msg instanceof Player_team) {
			Player_team pg = (Player_team) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_team pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class PlayerGoldSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Integer) {// 收到请求角色消息
				Integer id = (Integer) msg;
				Optional<Player_team> op = hmCache.get(id);
				Player_team pg = new Player_team();
				if(op.isPresent())
				{
					pg = op.get();
				}else
				{
					pg.setAction(DBCache.FAIL);
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class PlayerGoldInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_team> hmInsert = new HashMap<Integer, Player_team>();
		private HashMap<Integer, Player_team> hmUpDate = new HashMap<Integer, Player_team>();
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
			if (msg instanceof Player_team) {// 收到请求角色消息
				Player_team pg = (Player_team) msg;
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
					Player_team[] pgs = new Player_team[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_teamDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_team[] pgs = new Player_team[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_teamDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
