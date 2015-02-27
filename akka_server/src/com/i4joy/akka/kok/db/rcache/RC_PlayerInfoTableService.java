package com.i4joy.akka.kok.db.rcache;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGreate;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_userPlayersGet;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_playerInfoGreate;
import com.i4joy.akka.kok.db.wqueue.WQ_PlayerInfoTableService;
import com.ump.impl.JdbcPlayer_infoDAO;
import com.ump.model.Player_info;

public class RC_PlayerInfoTableService extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(RC_PlayerInfoTableService.class, dataSource, DBName);
	}

	public static HashMap<String, Player_info> hm = new HashMap<String, Player_info>();// key:playerName
	public static HashMap<String, ArrayList<Player_info>> userHM = new HashMap<String, ArrayList<Player_info>>();// key:userName
	private static JdbcPlayer_infoDAO dao;
	private ActorRef getWorker;
	private ActorRef createWorker;
	private int nrOfWorkers = 16;// 初始路由工人数量
	public static final String topic = "player_info";// 表名字
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private static int maxPlayerId = 1;

	public RC_PlayerInfoTableService(DataSource dataSource, String DBName) {
		dao = new JdbcPlayer_infoDAO(dataSource);// 初始化处理DAO
		getWorker = getContext().actorOf(Props.create(PlayerInfoGetWorker.class).withRouter(new RandomRouter(nrOfWorkers)));// 多个处理
		createWorker = getContext().actorOf(Props.create(PlayerInfoCreateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
		hm = dao.getALL();// 缓存所有的角色信息
		// 缓存所有帐号和角色的关系信息
		Player_info[] playerInfos = new Player_info[hm.size()];
		hm.values().toArray(playerInfos);
		for (Player_info player_info : playerInfos) {
			if (player_info.getPlayer_id() > maxPlayerId) {
				maxPlayerId = player_info.getPlayer_id();
			}
			addUserHM(player_info);
		}
	}

	// 用户添加新角色
	public static void addUserHM(Player_info player_info) {
		if (userHM.containsKey(player_info.getUsername())) {
			userHM.get(player_info.getUsername()).add(player_info);
		} else {
			ArrayList<Player_info> list = new ArrayList<Player_info>();
			list.add(player_info);
			userHM.put(player_info.getUsername(), list);
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof RC_playerInfoGet || msg instanceof RC_userPlayersGet) {// 请求玩家信息
			getWorker.tell(msg, getSender());
		} else if (msg instanceof RC_playerInfoGreate)// 创建玩家
		{
			createWorker.tell(msg, getSender());
		}
	}

	// 创建角色工人
	public static class PlayerInfoCreateWorker extends UntypedActor {

		private HashMap<String, Player_info> needSave = new HashMap<String, Player_info>();// 需要保存的
		private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

		// 创建角色失败
		public void createFail(ActorRef sender) {
			RC_playerInfoGreate.Builder builder = RC_playerInfoGreate.newBuilder();
			builder.setCreateOk(false);
			sender.tell(builder.build(), getSelf());
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof RC_playerInfoGreate) {// 收到创建角色的请求
				RC_playerInfoGreate rc = (RC_playerInfoGreate) msg;
				String playerName = rc.getPlayerName();
				String userName = rc.getUserName();
				int serverId = rc.getServerId();
				byte db_id = (byte) rc.getDbId();
				// 创建角色
				Player_info p = new Player_info();
				p.setPlayer_name(playerName);
				p.setUsername(userName);
				p.setServer_id((short) serverId);
				p.setDb_id(db_id);

				if (hm.containsKey(playerName)) {
					createFail(getSender());// 角色缓存里有相同名字
				} else if (needSave.containsKey(playerName)) {
					createFail(getSender());// 等待创建的角色缓存里有相同名字
				} else {
					maxPlayerId++;
					p.setPlayer_id(maxPlayerId);
					needSave.put(p.getPlayer_name(), p);// 添加到等待创建的角色列表里
					hm.put(playerName, p);// 添加到所有角色的缓存里
					WQ_playerInfoGreate.Builder wqBuilder = WQ_playerInfoGreate.newBuilder();
					wqBuilder.setPlayerId(maxPlayerId);
					wqBuilder.setPlayerName(playerName);
					wqBuilder.setServerId(serverId);
					wqBuilder.setUserName(userName);
					wqBuilder.setDbId(db_id);
					mediator.tell(new Publish(WQ_PlayerInfoTableService.tableName, wqBuilder.build()), getSelf());// 发送给wq
					RC_playerInfoGreate.Builder builder = RC_playerInfoGreate.newBuilder();
					builder.setCreateOk(true);
					builder.setPlayerId(p.getPlayer_id());
					getSender().tell(builder.build(), getSelf());// 通知创建角色成功
					addUserHM(p);// 添加到帐号角色关系表
				}
			} else if (msg instanceof WQ_playerInfoGreate)// 收到角色创建成功的消息
			{
				WQ_playerInfoGreate wq = (WQ_playerInfoGreate) msg;
				String playerName = wq.getPlayerName();
				needSave.remove(playerName);// 从角色创建等待列表里删除
				if (wq.getCreateOk())// 如果创建成功
				{
					Player_info p = new Player_info();
					p.setPlayer_name(playerName);
					p.setUsername(wq.getUserName());
					p.setServer_id((short) wq.getServerId());
					p.setLast_time(System.currentTimeMillis());
				}
			}

		}
	}

	public static class PlayerInfoGetWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof RC_playerInfoGet) {// 收到请求角色消息
				RC_playerInfoGet get = (RC_playerInfoGet) msg;
				RC_playerInfoGet.Builder builder = RC_playerInfoGet.newBuilder();
				String playerName = get.getPlayerName();
				Player_info pi = hm.get(playerName);
				if (pi == null) {
					builder.setHadName(false);
				} else {
					builder.setHadName(true);
					builder.setPlayerName(playerName);
					builder.setServerId(pi.getServer_id());
					builder.setUserName(pi.getUsername());
					builder.setPlayerId(pi.getPlayer_id());
					builder.setDbId(pi.getDb_id());
				}
				getSender().tell(builder.build(), getSelf());
			} else if (msg instanceof RC_userPlayersGet) {// 收到请求帐号角色信息
				RC_userPlayersGet up = (RC_userPlayersGet) msg;
				String userName = up.getUsername();// 帐号
				RC_userPlayersGet.Builder builder = RC_userPlayersGet.newBuilder();
				ArrayList<Player_info> list = new ArrayList<Player_info>();
				if (userHM.containsKey(userName)) {// 帐号玩家缓存里有
					list = userHM.get(userName);
				}
				JSONObject[] jsons = new JSONObject[list.size()];
				for (int i = 0; i < list.size(); i++) {
					Player_info temp = list.get(i);
					jsons[i] = new JSONObject();
					jsons[i].put("name", temp.getPlayer_name());// 角色名字
					jsons[i].put("serverId", temp.getServer_id());// 服务器ID
					jsons[i].put("last_time", temp.getLast_time());// 最后登录时间
					jsons[i].put("id", temp.getPlayer_id());// 角色ID
				}
				JSONObject json = new JSONObject();
				json.put("players", jsons);
				builder.setUserPlayers(json.toString());
				getSender().tell(builder.build(), getSelf());
			}
		}

	}

}
