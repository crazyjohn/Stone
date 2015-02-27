package com.i4joy.akka.kok.db.rw;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcPlayer_emailsDAO;
import com.ump.model.DBCache;
import com.ump.model.Player_emails;

public class RW_Player_emailsTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_Player_emailsTableService.class, dataSource, DBName);
	}

	private String tableName = "player_emails";
	private final String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private String routerName = "router" + tableName;
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的
	private ActorRef playerEmailsGet;
	private ActorRef playerEmailsInsert;

	private static HashMap<Integer, ArrayList<Player_emails>> getHmCache = new HashMap<Integer, ArrayList<Player_emails>>();

	public RW_Player_emailsTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;

	}

	@Override
	public void preStart() throws Exception {
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + "#" + tableName, getSelf()), getSelf());
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		playerEmailsGet = getContext().actorOf(Props.create(PlayerEmailsSelectWorker.class).withRouter(new RandomRouter(nrOfWorkers)), routerName);// 多个处理
		playerEmailsInsert = getContext().actorOf(Props.create(PlayerEmailsInsertWorker.class));
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
		if (msg instanceof Player_emails) {
			Player_emails p = (Player_emails) msg;
			if (p.getAction() == DBCache.SELECT) {
				playerEmailsGet.tell(p, getSender());
			} else if (p.getAction() == DBCache.INSERT) {
				playerEmailsInsert.tell(p, getSender());
			}
		}
	}

	public static void addCacheOne(Player_emails email) {
		if (getHmCache.containsKey(email.getPlayer_id())) {
			ArrayList<Player_emails> list = getHmCache.get(email.getPlayer_id());
			list.add(email);
		} else {
			ArrayList<Player_emails> list = new ArrayList<Player_emails>();
			list.add(email);
			getHmCache.put(email.getPlayer_id(), list);
		}
	}

	public static void addCacheList(ArrayList<Player_emails> emailList, int playerId) {
		if (getHmCache.containsKey(playerId)) {
			ArrayList<Player_emails> list = getHmCache.get(playerId);
			list.addAll(emailList);
		} else {
			getHmCache.put(playerId, emailList);
		}
	}

	public static class PlayerEmailsSelectWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player_emails) {
				int playerId = ((Player_emails) msg).getPlayer_id();
				ArrayList<Player_emails> list = JdbcPlayer_emailsDAO.getInstance().getList(playerId);
				addCacheList(list, playerId);
				getSender().tell(list, getSelf());
			}
		}
	}

	public static class PlayerEmailsInsertWorker extends UntypedActor {
		private ArrayList<Player_emails> list = new ArrayList<Player_emails>();
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
			if (msg instanceof Player_emails) {
				Player_emails email = (Player_emails) msg;
				list.add(email);
				addCacheOne(email);
			} else if (msg instanceof Heart) {
				int len = list.size();
				if (len > 0) {
					Player_emails[] ps = new Player_emails[len];
					list.toArray(ps);
					JdbcPlayer_emailsDAO.getInstance().addList(ps);
					list.clear();
				}
			}
		}
	}
}
