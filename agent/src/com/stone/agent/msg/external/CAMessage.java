package com.stone.agent.msg.external;

import akka.actor.ActorRef;

import com.stone.core.msg.IActorMessage;
import com.stone.core.msg.ProtobufMessage;

public class CAMessage extends ProtobufMessage implements IActorMessage {

	public CAMessage(int messageType) {
		super(messageType);
	}

	@Override
	public ActorRef getPlayerActor() {
		return this.session.getActor();
	}

}
