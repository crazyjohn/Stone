package com.stone.core.session;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

/**
 * The game actor session;
 * 
 * @author crazyjohn
 *
 */
public class BaseActorSession extends BaseSession implements IActorSession {
	protected ActorRef actor;

	public BaseActorSession(IoSession session) {
		super(session);
	}

	public void setActor(ActorRef actor) {
		this.actor = actor;
	}

	@Override
	public ActorRef getActor() {
		return actor;
	}

	@Override
	public String toString() {
		return session.toString();
	}

}
