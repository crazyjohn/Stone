package com.i4joy.akka.kok.db.rw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.pattern.Patterns;
import akka.routing.RandomRouter;
import akka.util.Timeout;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcPlayer_mercenarysDAO;
import com.ump.impl.JdbcTeamDAO;
import com.ump.model.DBCache;
import com.ump.model.Player;
import com.ump.model.Player_mercenarys;
import com.ump.model.Team;

public class RW_Player_mercenarysTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_mercenarysTableService.class, dataSource, DBName);
	}

	// private static HashMap<Integer, Player_mercenarys> hmCache = new
	// HashMap<Integer, Player_mercenarys>();
	private static LoadingCache<Integer, Optional<Player_mercenarys>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	// private String tableName = "player_mercenarys";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_Player_mercenarysTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_mercenarys>>() {
			@Override
			public Optional<Player_mercenarys> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_mercenarysDAO.getInstance().getPlayer_mercenarys(key));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(Player_mercenarysSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(Player_mercenarysInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + Player_mercenarys.tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + Player_mercenarys.tableName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Player_mercenarys) {
			Player_mercenarys pg = (Player_mercenarys) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_mercenarys pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class Player_mercenarysSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_mercenarys) {// 收到请求角色消息
				Player_mercenarys pg = (Player_mercenarys) msg;
				Optional<Player_mercenarys> op = hmCache.get(pg.getPlayer_id());
				if (op.isPresent()) {
					pg = op.get();
				} else {
					pg.setAction(DBCache.FAIL);
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class Player_mercenarysInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_mercenarys> hmInsert = new HashMap<Integer, Player_mercenarys>();
		private HashMap<Integer, Player_mercenarys> hmUpDate = new HashMap<Integer, Player_mercenarys>();
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
			if (msg instanceof Player_mercenarys) {// 收到请求角色消息
				Player_mercenarys pg = (Player_mercenarys) msg;
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
					Player_mercenarys[] pgs = new Player_mercenarys[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_mercenarysDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_mercenarys[] pgs = new Player_mercenarys[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_mercenarysDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}

}
