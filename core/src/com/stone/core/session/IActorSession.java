package com.stone.core.session;

import akka.actor.ActorRef;

/**
 * The actor session;
 * 
 * @author crazyjohn
 *
 */
public interface IActorSession extends ISession {

	/**
	 * Get the actor;
	 * 
	 * @return
	 */
	public ActorRef getActor();
}
