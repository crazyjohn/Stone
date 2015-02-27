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
import com.ump.impl.JdbcPlayer_mercenary_recordDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_mercenary_record;

public class RW_Player_mercenary_recordTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_mercenary_recordTableService.class, dataSource, DBName);
	}

	public RW_Player_mercenary_recordTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	private static LoadingCache<Integer, Optional<Player_mercenary_record>> hmCache;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;
	private String DBName;

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<Player_mercenary_record>>() {
			@Override
			public Optional<Player_mercenary_record> load(Integer key) throws Exception {
				return Optional.fromNullable(JdbcPlayer_mercenary_recordDAO.getInstance().getPlayerMercenaryRecord(key));
			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(PlayerMercenaryRecordSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(PlayerMercenaryRecordInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + Player_mercenary_record.tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + Player_mercenary_record.tableName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBCache) {
			DBCache pg = (DBCache) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			} else if (msg instanceof Heart) {
				hmCache.cleanUp();
			}
		}
	}

	public static void addCache(Player_mercenary_record pg) {
		hmCache.put(pg.getPlayer_id(), Optional.fromNullable(pg));
	}

	public static class PlayerMercenaryRecordSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_mercenary_record) {// 收到请求角色消息
				Player_mercenary_record pmg = (Player_mercenary_record) msg;
				Optional<Player_mercenary_record> pg = hmCache.get(pmg.getPlayer_id());
				if (pg.isPresent()) {
					pmg = pg.get();
				} else {
					pmg.setAction(DBCache.FAIL);
				}
				getSender().tell(pmg, getSelf());
			}
		}
	}

	public static class PlayerMercenaryRecordInsertUpDateWorker extends UntypedActor {
		private HashMap<Integer, Player_mercenary_record> hmInsert = new HashMap<Integer, Player_mercenary_record>();
		private HashMap<Integer, Player_mercenary_record> hmUpDate = new HashMap<Integer, Player_mercenary_record>();
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
			if (msg instanceof Player_mercenary_record) {// 收到请求角色消息
				Player_mercenary_record pg = (Player_mercenary_record) msg;
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
					Player_mercenary_record[] pgs = new Player_mercenary_record[len];
					hmInsert.values().toArray(pgs);
					JdbcPlayer_mercenary_recordDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Player_mercenary_record[] pgs = new Player_mercenary_record[len];
					hmUpDate.values().toArray(pgs);
					JdbcPlayer_mercenary_recordDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
