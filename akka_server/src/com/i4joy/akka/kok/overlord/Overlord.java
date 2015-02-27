package com.i4joy.akka.kok.overlord;

import java.util.Arrays;
import java.util.HashMap;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.routing.RandomRouter;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.overlord.protocol.ConnectionClose;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorAddress;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorKill;
import com.i4joy.akka.kok.overlord.protocol.PlayerAddress;
import com.i4joy.akka.kok.overlord.protocol.PlayerCreate;
import com.i4joy.akka.kok.overlord.protocol.PlayersGroupStatus;
import com.i4joy.akka.kok.protocol.PClosePlayerProxy;

public class Overlord extends UntypedActor {
	// PlayersGroup // 状态表
	private static HashMap<String, PlayersGroupStatus> playersGroupsHm = new HashMap<String, PlayersGroupStatus>();
	private static HashMap<Integer, PlayerStatus> playerStatusHm = new HashMap<Integer, PlayerStatus>();
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	private ActorRef playersGroupStatusWorker;
	private ActorRef playerAddressWorker;
	private ActorRef createPlayer;
	private int nrOfWorkers = 16;// 初始路由工人数量

	public static Props props(String name) {
		return Props.create(Overlord.class, name);
	}

	public Overlord(String name) {
	}

	public static String getPlayerGroup() {
		PlayersGroupStatus[] pgs = new PlayersGroupStatus[playersGroupsHm.size()];
		playersGroupsHm.values().toArray(pgs);
		Arrays.sort(pgs);
		return pgs[0].getName();
	}

	@Override
	public void preStart() throws Exception {
		playersGroupStatusWorker = getContext().actorOf(Props.create(PlayersGroupStatusWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		playerAddressWorker = getContext().actorOf(Props.create(PlayerAddressWorker.class).withRouter(new RandomRouter(nrOfWorkers)));
		createPlayer = getContext().actorOf(Props.create(ConnectionCreateWorker.class));
		mediator.tell(new DistributedPubSubMediator.Subscribe(Property.OVERLORDNAME, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof PlayersGroupStatus) {// playerGroup 心跳状态
			playersGroupStatusWorker.tell(message, getSender());
		} else if (message instanceof PlayerAddress) {// 通知所有PlayerGroup玩家登录
			playerAddressWorker.tell(message, getSender());
		} else {// IO发过来的
			createPlayer.tell(message, getSender());
		}
	}

	public static class PlayersGroupStatusWorker extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof PlayersGroupStatus) {// playerGroup 心跳状态
				PlayersGroupStatus ms = (PlayersGroupStatus) msg;// 状态
				ms.setLastUpdate(System.currentTimeMillis());// 设置更新时间
				playersGroupsHm.put(ms.getName(), ms);
			}
		}
	}

	public static class PlayerAddressWorker extends UntypedActor {
		final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

		@Override
		public void onReceive(Object message) throws Exception {
			if (message instanceof PlayerAddress) {
				PlayerAddress pa = (PlayerAddress) message;
				PlayersGroupStatus[] pgs = new PlayersGroupStatus[playersGroupsHm.size()];
				playersGroupsHm.values().toArray(pgs);
				for (int i = 0; i < pgs.length; i++) {
					if (!pa.getPlayerGroupName().equals(pgs[i].getName())) {
						mediator.tell(new Publish(pgs[i].getName(), message), getSelf());
					}

				}
			}
		}
	}

	public static class ConnectionCreateWorker extends UntypedActor {
		final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof PlayerCreate) {
				PlayerCreate pc = (PlayerCreate) msg;
				String playerGroupName = Overlord.getPlayerGroup();
				PlayerStatus ps = new PlayerStatus();
				ps.playerId = pc.getPlayerId();
				ps.connection = getSender();
				ps.playerName = pc.getPlayerName();
				ps.playerGroupName = playerGroupName;
				PlayerStatus psHad = playerStatusHm.get(ps.playerId);
				if (psHad != null) {
					if (psHad.connection != null) {
						psHad.connection.tell(new PClosePlayerProxy("repetition"), getSelf());
					}
					psHad.connection = getSender();
					psHad.privateTime = System.currentTimeMillis();
//					PlayerAddress pr = new PlayerAddress();
//					pr.setPlayerId(pc.getPlayerId());
//					pr.setPlayerGroupName(psHad.address);
//					pr.setPlayerName(pc.getPlayerName());
//					ps.connection.tell(pr, getSelf());
				} else {
					mediator.tell(new Publish(playerGroupName, pc), getSelf());
					playerStatusHm.put(ps.playerId, ps);
				}

			} else if (msg instanceof PlayerActorAddress) {
				PlayerActorAddress pr = (PlayerActorAddress) msg;
				PlayerStatus ps = playerStatusHm.get(pr.playerId);
				if (ps != null) {
					ps.address = pr.address;
					ps.connection.tell(pr, getSelf());
				}
			} else if (msg instanceof ConnectionClose) {
				ConnectionClose cc = (ConnectionClose) msg;
				PlayerStatus ps = playerStatusHm.get(cc.playerId);
				if (ps != null) {
					ps.connection = null;
				}
			} else if (msg instanceof PlayerActorKill) {
				PlayerActorKill pak = (PlayerActorKill) msg;
				PlayerStatus ps = playerStatusHm.get(pak.playerId);
				if (ps != null) {
					if (System.currentTimeMillis() - ps.privateTime > Property.SECOND10) {
						if (ps.connection != null) {
							ps.connection.tell(new PClosePlayerProxy("no request  too long"), getSelf());
							ps.connection = null;
						}
						playerStatusHm.remove(pak.playerId);
						publishPlayerActorKill(pak);
					}
				} else {
					publishPlayerActorKill(pak);
				}
			}
		}

		public void publishPlayerActorKill(PlayerActorKill pak) {
			PlayersGroupStatus[] pgs = new PlayersGroupStatus[playersGroupsHm.size()];
			playersGroupsHm.values().toArray(pgs);
			for (int i = 0; i < pgs.length; i++) {
				mediator.tell(new Publish(pgs[i].getName(), pak), getSelf());
			}
		}
	}

}

class PlayerStatus {
	public int playerId;
	public String playerName;
	public ActorRef connection;
	public String playerGroupName;
	public String address;
	public long privateTime;
}
