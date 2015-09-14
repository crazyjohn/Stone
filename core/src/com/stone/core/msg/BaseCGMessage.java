package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.BaseActorSession;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseCGMessage extends BaseMessage implements CGMessage {
	protected BaseActorSession session;

	@Override
	public BaseActorSession getSession() {
		return session;
	}

	@Override
	public void setSession(BaseActorSession session) {
		this.session = session;
	}

	@Override
	public ActorRef getPlayerActor() {
		return session.getActor();
	}

}
