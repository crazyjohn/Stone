package com.i4joy.akka.kok.db.rw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.ump.impl.JdbcPlayerDAO;
import com.ump.model.DBCache;
import com.ump.model.Player;

public class RW_PlayerTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RW_PlayerTableService.class, dataSource, DBName);
	}

	private static HashMap<Integer, Player> hmIdGet = new HashMap<Integer, Player>();

	private ActorRef getWorker;
	private ActorRef upDateWorker;
	private ActorRef insertWorker;

	private String tableName = "player";
	private String DBName;
	private int nrOfWorkers = 16;// 初始路由工人数量
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private Cancellable heart;// 心跳 清理缓存中超时的

	public RW_PlayerTableService(DataSource dataSource, String DBName) {
		this.DBName = DBName;
	}

	@Override
	public void preStart() throws Exception {
		getWorker = getContext().actorOf(Props.create(PlayerGetWorker.class).withRouter(new RandomRouter(nrOfWorkers)));// 多个处理
		upDateWorker = getContext().actorOf(Props.create(PlayerUpdateWorker.class));
		insertWorker = getContext().actorOf(Props.create(PlayerInsertWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(DBName + "#" + tableName, getSelf()), getSelf());
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
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
		if (msg instanceof Player) {
			Player player = (Player) msg;
			if (player.getAction() == DBCache.INSERT) {
				insertWorker.tell(msg, getSender());
			} else if (player.getAction() == DBCache.SELECT) {
				getWorker.tell(player, getSender());
			} else if (player.getAction() == DBCache.UPDATE) {
				upDateWorker.tell(player, getSender());
			}
		} else if (msg instanceof Heart) {
			long curTime = System.currentTimeMillis();
			Iterator<Map.Entry<Integer, Player>> iterator = hmIdGet.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Player> entry = iterator.next();
				Player state = entry.getValue();
				if (state.getCacheTime() - curTime > Property.MINUTE) {
					iterator.remove();
				}
			}
		}
	}

	public static Player doPlayer(Player player, ActorRef mediator, String DBName) {

		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(DBName + "#player", player), timeout);
		try {
			Object o = Await.result(future, timeout.duration());
			return (Player) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addCache(Player player) {
		player.setCacheTime(System.currentTimeMillis());
		hmIdGet.put(player.getPlayer_id(), player);
	}

	public static class PlayerGetWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof Player) {// 收到请求角色消息
				Player p = (Player) msg;
				Player player = new Player();
				player = hmIdGet.get(p.getPlayer_id());
				if (player == null) {
					player = JdbcPlayerDAO.getInstance().getPlayerById(p.getPlayer_id());
					if (player != null) {
						addCache(player);
					}
				}
				if (player == null) {
					player = new Player();
					player.setPlayer_id(0);
				}
				getSender().tell(player, getSelf());
			}
		}
	}

	public static class PlayerUpdateWorker extends UntypedActor {
		private Cancellable heart;// 心跳 清理缓存中超时的
		private static HashMap<Integer, Player> hmNeedUpDate = new HashMap<Integer, Player>();

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
			if (msg instanceof Player) {// 收到请求角色消息
				Player player = (Player) msg;
				hmNeedUpDate.put(player.getPlayer_id(), player);
				addCache(player);
			} else if (msg instanceof Heart) {
				int len = hmNeedUpDate.size();
				if(len > 0)
				{
					Player[] players = new Player[len];
					hmNeedUpDate.values().toArray(players);
					JdbcPlayerDAO.getInstance().upDateList(players);
					hmNeedUpDate.clear();	
				}
				
			}
		}

	}

	public static class PlayerInsertWorker extends UntypedActor {
		private HashMap<String, Player> hm = new HashMap<String, Player>();
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
			if (msg instanceof Player) {// 收到请求角色消息
				Player player = (Player) msg;
				if (!hm.containsKey(player.getPlayer_name())) {
					hm.put(player.getPlayer_name(), player);
					addCache(player);
				} else {
					player.setAction(DBCache.FAIL);
				}
				getSender().tell(player, getSelf());
			} else if (msg instanceof Heart) {
				int len = hm.size();
				if (len > 0) {
					Player[] players = new Player[len];
					hm.values().toArray(players);
					long time = System.currentTimeMillis();
					JdbcPlayerDAO.getInstance().addList(players);
					System.out.println(len + " bath add Player use time " + (System.currentTimeMillis() - time));
					hm.clear();
				}
			}
		}

	}

}
