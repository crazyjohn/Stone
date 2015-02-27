package com.i4joy.akka.kok.monster;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.io.protocol.Heart;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.akka.kok.overlord.protocol.PlayerActorKill;
import com.i4joy.akka.kok.protobufs.ProtobufFactory;
import com.i4joy.akka.kok.protobufs.deal.PDeal;

public class PlayerActor extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	private Cancellable heart;// 心跳 清理缓存中超时的
	private long lastTime = 0;
	private String address;
	@Override
	public void postStop() throws Exception {
		// Tools.println("Player kill " + playerId + " " + playerName + " " +
		// serverId, logger);
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe("playerId" + playerEntity.getPlayerId(), getSelf()), getSelf());
		mediator.tell(new DistributedPubSubMediator.Unsubscribe("playerName" + playerEntity.getPlayerName(), getSelf()), getSelf());
		mediator.tell(new DistributedPubSubMediator.Unsubscribe("server" + playerEntity.getPlayerInfo().getServer_id(), getSelf()), getSelf());
		playerEntity = null;
		super.postStop();
	}

	public static Props props(String uuid, String playerName, int serverId,String address) {
		return Props.create(PlayerActor.class, uuid, playerName, serverId,address);
	}

	private PlayerEntity playerEntity;
	private ActorRef pDeal;

	private PlayerActor(String uuid, String playerName, int serverId,String address) {
		playerEntity = new PlayerEntity(playerName, mediator, Integer.parseInt(uuid));
		this.address = address;
	}

	@Override
	public void preStart() throws Exception {
		// getPlayerInfo();
		// getPlayer();
		mediator.tell(new DistributedPubSubMediator.Subscribe("playerId" + playerEntity.getPlayerId(), getSelf()), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe("playerName" + playerEntity.getPlayerName(), getSelf()), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe("server" + playerEntity.getPlayerInfo().getServer_id(), getSelf()), getSelf());
		pDeal = getContext().actorOf(PDeal.props(playerEntity));
		heart = getContext().system().scheduler().schedule(Duration.create(60, "seconds"), Duration.create(60, "seconds"), getSelf(), Heart.instance, getContext().dispatcher(), getSelf());
		lastTime = System.currentTimeMillis();
		super.preStart();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof byte[]) {
			byte[] data = (byte[]) msg;
			pDeal.tell(ProtobufFactory.getProtobuf(data), getSender());
			lastTime = System.currentTimeMillis();
		} else if (msg instanceof Heart) {
			if (System.currentTimeMillis() - lastTime > Property.MINUTE30) {
				PlayerActorKill pak = new PlayerActorKill();
				pak.playerId = playerEntity.getPlayerId();
				pak.address = address;
				mediator.tell(new Publish(Property.OVERLORDNAME, pak), getSelf());
			}
		}
	}
	


}
