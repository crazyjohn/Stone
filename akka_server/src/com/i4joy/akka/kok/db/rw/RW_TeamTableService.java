package com.i4joy.akka.kok.db.rw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
import com.ump.impl.JdbcTeamDAO;
import com.ump.model.DBCache;
import com.ump.model.Team;

public class RW_TeamTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_TeamTableService.class, dataSource, DBName);
	}

	private static LoadingCache<Integer, Optional<List<Team>>> hmCache;
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String tableName = "team";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_TeamTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<List<Team>>>() {
			@Override
			public Optional<List<Team>> load(Integer key) throws Exception {
				String[] strs = new String[8];
				for (int i = 0; i < 8; i++) {
					strs[i] = key + "_" + i;
				}
				return Optional.fromNullable(JdbcTeamDAO.getInstance().getTeams(strs));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(TeamSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)), routerName);
		insertUpdateWorker = getContext().actorOf(Props.create(TeamInsertUpDateWorker.class));
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
		if (msg instanceof Team) {
			Team pg = (Team) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Team team) throws ExecutionException {
		Optional<List<Team>> op = hmCache.get(team.getPlayer_id());
		if (op.isPresent()) {
			op.get().add(team);
		} else {
			List<Team> list = new ArrayList<Team>();
			list.add(team);
			hmCache.put(team.getPlayer_id(), Optional.fromNullable(list));
		}
	}

	public static class TeamSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Team) {// 收到请求角色消息
				Team team = (Team) msg;
				List<Team> list = null;
				Optional<List<Team>> op = hmCache.get(team.getPlayer_id());
				if (op.isPresent()) {
					list = op.get();
				} else {
					list = new ArrayList<Team>();
				}
				getSender().tell(list, getSelf());
			}
		}
	}

	public static class TeamInsertUpDateWorker extends UntypedActor {
		private HashMap<String, Team> hmInsert = new HashMap<String, Team>();
		private HashMap<String, Team> hmUpDate = new HashMap<String, Team>();
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
			if (msg instanceof Team) {// 收到请求角色消息
				Team pg = (Team) msg;
				if (pg.getAction() == DBCache.INSERT) {
					hmInsert.put(pg.getPlayer_id()+"_"+pg.getPostion(), pg);
//					System.out.println("TeamInsertUpDateWorker   "+pg.getPlayer_id()+"_"+pg.getPostion());
					addCache(pg);
				} else if (pg.getAction() == DBCache.UPDATE) {
					hmUpDate.put(pg.getPlayer_id()+"_"+pg.getPostion(), pg);
					addCache(pg);
//					System.out.println("TeamInsertUpDateWorker   "+pg.getPlayer_id()+"_"+pg.getPostion());
				}

			} else if (msg instanceof Heart) {
				int len = hmInsert.size();
				if (len > 0) {
					Team[] pgs = new Team[len];
					hmInsert.values().toArray(pgs);
					JdbcTeamDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Team[] pgs = new Team[len];
					hmUpDate.values().toArray(pgs);
					JdbcTeamDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
