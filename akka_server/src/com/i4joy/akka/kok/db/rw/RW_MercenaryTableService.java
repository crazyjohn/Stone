package com.i4joy.akka.kok.db.rw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

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
import com.ump.impl.JdbcMercenaryDAO;
import com.ump.model.DBCache;
import com.ump.model.Mercenary;
import com.ump.model.Player_mercenarys;

public class RW_MercenaryTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_MercenaryTableService.class, dataSource, DBName);
	}

	private static LoadingCache<Integer, Optional<List<Mercenary>>> hmCache;
	private static HashMap<Integer, String[]> listCache = new HashMap<Integer, String[]>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_MercenaryTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		hmCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Optional<List<Mercenary>>>() {
			@Override
			public Optional<List<Mercenary>> load(Integer key) throws Exception {
				String[] strs = listCache.get(key);
				if (strs == null) {
					return Optional.fromNullable(null);
				} else {
					listCache.remove(key);
					return Optional.fromNullable(JdbcMercenaryDAO.getInstance().getMercenaryList(strs));
				}

			}
		});
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(MercenarySelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(MercenaryUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + Mercenary.tableName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(DBName + Mercenary.tableName, getSelf()), getSelf());
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

	public static void addCache(Mercenary pg) throws ExecutionException {
		Optional<List<Mercenary>> op = hmCache.get((int) pg.getOwner_id());
		if (op.isPresent()) {
			op.get().add(pg);
		} else {
			List<Mercenary> list = new ArrayList<Mercenary>();
			list.add(pg);
			hmCache.put((int) pg.getOwner_id(), Optional.fromNullable(list));
		}
	}

	public static class MercenarySelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_mercenarys) {// 收到请求角色消息
				Player_mercenarys pm = (Player_mercenarys) msg;
				List<JSONObject> jsonList = pm.getMercenaryList();
				String[] strs = new String[jsonList.size()];
				for (int i = 0; i < jsonList.size(); i++) {
					strs[i] = jsonList.get(i).getString("entityId");
				}
				listCache.put(pm.getPlayer_id(), strs);
				Optional<List<Mercenary>> op = hmCache.get(pm.getPlayer_id());
				List<Mercenary> list = new ArrayList<Mercenary>();
				if (op.isPresent()) {
					list = op.get();
				}
				getSender().tell(list, getSelf());
			}
		}
	}

	public static class MercenaryUpDateWorker extends UntypedActor {
		private HashMap<Long, Mercenary> hmInsert = new HashMap<Long, Mercenary>();
		private HashMap<Long, Mercenary> hmUpDate = new HashMap<Long, Mercenary>();
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
			if (msg instanceof Mercenary) {// 收到请求角色消息
				Mercenary pg = (Mercenary) msg;
				if (pg.getAction() == DBCache.INSERT) {
					addCache(pg);
					hmInsert.put(pg.getOwner_id(), pg);
				} else if (pg.getAction() == DBCache.UPDATE) {
					addCache(pg);
					hmUpDate.put(pg.getOwner_id(), pg);
				}

			} else if (msg instanceof Heart) {
				int len = hmInsert.size();
				if (len > 0) {
					Mercenary[] pgs = new Mercenary[len];
					hmInsert.values().toArray(pgs);
					JdbcMercenaryDAO.getInstance().addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					Mercenary[] pgs = new Mercenary[len];
					hmUpDate.values().toArray(pgs);
					JdbcMercenaryDAO.getInstance().upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
