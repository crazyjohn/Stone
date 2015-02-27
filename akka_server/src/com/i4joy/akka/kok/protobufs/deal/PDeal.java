package com.i4joy.akka.kok.protobufs.deal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;

import com.i4joy.akka.kok.monster.PlayerActor;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.akka.kok.protobufs.KOKPacket.HeartRequest;
import com.i4joy.util.Tools;

public class PDeal extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());
	final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();

	public static Props props(PlayerEntity player) {
		return Props.create(PDeal.class, player);
	}

	private final PlayerEntity player;

	public PDeal(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public void postStop() throws Exception {
		Tools.printlnInfo("PDeal stop", logger);
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof HeartRequest) {
			PHeart.deal(player, (HeartRequest) msg, mediator, getSender(), getSelf());
		}
	}

}
