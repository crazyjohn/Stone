package com.stone.core.session;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

/**
 * The game actor session;
 * 
 * @author crazyjohn
 *
 */
public class GameActorSession extends BaseSession implements IPlayerActorSession {
	protected ActorRef playerActor;

	public GameActorSession(IoSession session) {
		super(session);
	}

	public void setPlayerActor(ActorRef playerActor) {
		this.playerActor = playerActor;
	}

	@Override
	public ActorRef getPlayerActor() {
		return playerActor;
	}

	@Override
	public String toString() {
		return session.toString();
	}

}
