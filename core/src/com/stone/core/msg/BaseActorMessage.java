package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.BaseActorSession;

/**
 * Base session message;
 * @author crazyjohn
 *
 */
public abstract class BaseCAMessage extends BaseSessionMessage<BaseActorSession> implements CAMessage {

	@Override
	public ActorRef getPlayerActor() {
		return session.getActor();
	}

}
