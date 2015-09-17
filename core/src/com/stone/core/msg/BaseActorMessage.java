package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.BaseActorSession;

/**
 * Base session message;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseActorMessage extends BaseSessionMessage<BaseActorSession> implements IActorMessage {

	@Override
	public ActorRef getPlayerActor() {
		return session.getActor();
	}

}
