package com.i4joy.akka.kok.monster;

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
import akka.util.Timeout;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.camel.worker.LoginWorker;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.i4joy.akka.kok.io.protocol.Packet;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorAddress;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorKill;
import com.i4joy.akka.kok.overlord.protocol.PlayerAddress;
import com.i4joy.akka.kok.overlord.protocol.PlayerCreate;
import com.i4joy.akka.kok.overlord.protocol.PlayersGroupStatus;
import com.i4joy.util.Tools;

public class PlayersGroup extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	private final Cancellable registerTask;// 心跳
	final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	private MonsterWatchList monsterWatchList = new MonsterWatchList();
	private final String playersGroupName;

	public static Props props(String publishName) {
		return Props.create(PlayersGroup.class, publishName);
	}

	private PlayersGroup(String publishName) {
		this.playersGroupName = publishName;
		this.registerTask = getContext().system().scheduler()// 心跳 每10秒更新一次
				.schedule(Duration.create(10, "seconds"), Duration.create(10, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(publishName, getSelf()), getSelf());
	}

	@Override
	public void postStop() throws Exception {
		registerTask.cancel();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Heart) {// 心跳通知
			PlayersGroupStatus pgs = new PlayersGroupStatus();
			pgs.setName(playersGroupName);// 自己的名字
			pgs.setPlayerNum(monsterWatchList.getSize());// 玩家数量
			mediator.tell(new Publish(Property.OVERLORDNAME, pgs), getSelf());// 把自己的状态更新给OverLord
		} else if (msg instanceof PlayerCreate) {// 创建玩家通知
			PlayerCreate pc = (PlayerCreate) msg;
			monsterWatchList.addPlayerActor(pc, playersGroupName, getContext());
			monsterWatchList.addPlayerAddress(pc.getPlayerName(), pc.getPlayerId(), playersGroupName);
			PlayerActorAddress paa = new PlayerActorAddress();
			paa.playerId = pc.getPlayerId();
			paa.address = playersGroupName;
			mediator.tell(new Publish(Property.OVERLORDNAME, paa), getSelf());

			// 通知OverLordname
			// 目的是通知其他的PlayerGroup这个玩家的地址关系
			// 以便其他的PlayerGroup查询此玩家
			PlayerAddress pa = new PlayerAddress();// 生产玩家地址关系
			pa.setPlayerGroupName(playersGroupName);
			pa.setPlayerId(pc.getPlayerId());
			pa.setPlayerName(pc.getPlayerName());
			getSender().tell(pa, getSelf());// 通知广播的地址
		} else if (msg instanceof PlayerAddress) {// 获得其他PlayerGroup 的玩家地址关系
			PlayerAddress pa = (PlayerAddress) msg;
			// 添加到地址表里
			monsterWatchList.addPlayerAddress(pa.getPlayerName(), pa.getPlayerId(), playersGroupName);
			killPlayer(pa.getPlayerId());
		} else if (msg instanceof PlayerActorKill) {// 玩家下线
			PlayerActorKill kill = (PlayerActorKill) msg;
			clearPlayerAddress(kill.playerId);
			killPlayer(kill.playerId);
		} else if (msg instanceof Packet.msgInfo)// 获得封包
		{
			Packet.msgInfo packet = (Packet.msgInfo) msg;
			// 封包发给对应UUID的Player
			ActorRef playerActor = monsterWatchList.getPlayerActorById(Integer.parseInt(packet.getUuid()));
			if (playerActor != null) {
				playerActor.tell(packet, getSender());
			}
		}
	}

	public void clearPlayerAddress(int playerId) {
		monsterWatchList.removePlayerAddress(playerId, null);
	}

	public void killPlayer(int playerId) {
		monsterWatchList.removePlayerActor(playerId, null, getContext());
	}

	@SuppressWarnings("unchecked")
	public <T> T askPlayerActorByName(String playerName, Object message, Class<T> clazz) {
		try {
			ActorRef playerActor = monsterWatchList.getPlayerActorByName(playerName);
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			if (playerActor != null) {
				Future<Object> future = Patterns.ask(playerActor, message, timeout);
				return (T) Await.result(future, timeout.duration());
			} else {
				String groupName = monsterWatchList.getPlayerAddressByName(playerName);
				if (groupName != null) {
					Future<Object> future = Patterns.ask(mediator, new Publish("playerName" + playerName, message), timeout);
					return (T) Await.result(future, timeout.duration());
				} else {
					RC_playerInfoGet p = LoginWorker.getPlayerInfo(playerName, mediator);
					if (p != null) {
						PlayerCreate pr = new PlayerCreate();
						pr.setPlayerId(p.getPlayerId());
						pr.setPlayerName(playerName);
						pr.setServerId(p.getServerId());
						Future<Object> futureCreate = Patterns.ask(mediator, new Publish(Property.OVERLORDNAME, pr), timeout);
						PlayerActorAddress paa = (PlayerActorAddress) Await.result(futureCreate, timeout.duration());//需要等完成
						Future<Object> future = Patterns.ask(mediator, new Publish("playerName" + playerName, message), timeout);
						return (T) Await.result(future, timeout.duration());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Tools.printError(e, logger, null);
		}
		return null;

	}
}
