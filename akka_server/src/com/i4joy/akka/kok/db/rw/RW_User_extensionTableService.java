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
import com.ump.impl.JdbcUser_extensionDAO;
import com.ump.model.DBCache;
import com.ump.model.User_extension;

public class RW_User_extensionTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_User_extensionTableService.class, dataSource, DBName);
	}

	private static HashMap<String, User_extension> hmCache = new HashMap<String, User_extension>();
	private ActorRef selectWorker;
	private ActorRef insertUpdateWorker;

	public static final String topic = "user_extension";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的
	private static JdbcUser_extensionDAO dao;

	public RW_User_extensionTableService(DataSource dataSource, String DBName) {
		dao = new JdbcUser_extensionDAO(dataSource);
	}

	@Override
	public void preStart() throws Exception {
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		selectWorker = getContext().actorOf(Props.create(User_extensionSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		insertUpdateWorker = getContext().actorOf(Props.create(User_extensionInsertUpDateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());

		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(topic, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof User_extension) {
			User_extension pg = (User_extension) msg;
			if (pg.getAction() == DBCache.SELECT) {
				selectWorker.tell(pg, getSender());
			} else if (pg.getAction() == DBCache.INSERT || pg.getAction() == DBCache.UPDATE) {
				insertUpdateWorker.tell(pg, getSender());
			}

		} else if (msg instanceof Heart) {
			long curTime = System.currentTimeMillis();
			Iterator<Map.Entry<String, User_extension>> iterator = hmCache.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, User_extension> entry = iterator.next();
				User_extension state = entry.getValue();
				if (state.getCacheTime() - curTime > Property.MINUTE) {
					iterator.remove();
				}
			}
		}
	}

	public static void addCache(User_extension pg) {
		pg.setCacheTime(System.currentTimeMillis());
		hmCache.put(pg.getUsername(), pg);
	}

	public static class User_extensionSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof String) {// 收到请求角色消息
				String name = (String) msg;
				User_extension pg = hmCache.get(name);
				if (pg == null) {
					pg = dao.getUser_extension(name);
					if (pg == null) {
						pg = new User_extension();
					} else {
						addCache(pg);
					}
				}
				getSender().tell(pg, getSelf());
			}
		}
	}

	public static class User_extensionInsertUpDateWorker extends UntypedActor {
		private HashMap<String, User_extension> hmInsert = new HashMap<String, User_extension>();
		private HashMap<String, User_extension> hmUpDate = new HashMap<String, User_extension>();
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
			if (msg instanceof User_extension) {// 收到请求角色消息
				User_extension pg = (User_extension) msg;
				if (pg.getAction() == DBCache.INSERT) {
					addCache(pg);
					hmInsert.put(pg.getUsername(), pg);
				} else if (pg.getAction() == DBCache.UPDATE) {
					addCache(pg);
					hmUpDate.put(pg.getUsername(), pg);
				}

			} else if (msg instanceof Heart) {
				int len = hmInsert.size();
				if (len > 0) {
					User_extension[] pgs = new User_extension[len];
					hmInsert.values().toArray(pgs);
					dao.addList(pgs);
					hmInsert.clear();
				}

				len = hmUpDate.size();
				if (len > 0) {
					User_extension[] pgs = new User_extension[len];
					hmUpDate.values().toArray(pgs);
					dao.upDateList(pgs);
					hmUpDate.clear();
				}
			}
		}
	}
}
