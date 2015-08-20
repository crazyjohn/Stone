package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.GameActorSession;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseCGMessage extends BaseMessage implements CGMessage {
	protected GameActorSession session;

	@Override
	public GameActorSession getSession() {
		return session;
	}

	@Override
	public void setSession(GameActorSession session) {
		this.session = session;
	}

	@Override
	public ActorRef getPlayerActor() {
		return session.getPlayerActor();
	}

}
