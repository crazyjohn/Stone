package com.i4joy.akka.kok.db.wqueue;

import java.util.Arrays;
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

import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.WQ_playerInfoGreate;
import com.ump.impl.JdbcPlayer_infoDAO;
import com.ump.model.Player_info;

public class WQ_PlayerInfoTableService extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	public static Props props(DataSource dataSource, String DBName) {
		return Props.create(WQ_PlayerInfoTableService.class, dataSource, DBName);
	}

	private HashMap<String, Player_info> createHM = new HashMap<String, Player_info>();
	private final Cancellable heart;// 心跳 清理缓存中超时的
	private static JdbcPlayer_infoDAO dao;
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	public static final String tableName = "#WQplayer_info";// 表名字

	public WQ_PlayerInfoTableService(DataSource dataSource, String DBName) {
		dao = new JdbcPlayer_infoDAO(dataSource);// 初始化处理DAO
		heart = getContext().system().scheduler().schedule(Duration.create(5, "seconds"), Duration.create(5, "seconds"), getSelf(), "heart", getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(tableName, getSelf()), getSelf());
	}


	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof WQ_playerInfoGreate) {// 收到创建角色请求
			WQ_playerInfoGreate create = (WQ_playerInfoGreate) msg;

			Player_info p = new Player_info();
			p.setPlayer_name(create.getPlayerName());
			p.setServer_id((short) create.getServerId());
			p.setUsername(create.getUserName());
			p.setPlayer_id(create.getPlayerId());
			p.setDb_id((byte)create.getDbId());
			WQ_playerInfoGreate.Builder builder = WQ_playerInfoGreate.newBuilder();
			builder.setPlayerName(create.getPlayerName());
			builder.setServerId(create.getServerId());
			builder.setUserName(create.getUserName());

			if (!createHM.containsKey(p.getPlayer_name())) {
				builder.setCreateOk(true);
				createHM.put(p.getPlayer_name(), p);
			} else {
				builder.setCreateOk(false);
			}
			getSender().tell(builder.build(), getSelf());// 返回创建成功
		} else if (msg instanceof String) {// 心跳批量创建
			int len = createHM.size();
			if (len > 0) {
				Player_info[] ps = new Player_info[len];
				createHM.values().toArray(ps);
				Arrays.sort(ps);
				dao.addList(ps);
				createHM.clear();
			}

		}
	}

}
