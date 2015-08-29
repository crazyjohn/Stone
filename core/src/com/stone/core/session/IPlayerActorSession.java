package com.stone.core.session;

import akka.actor.ActorRef;

/**
 * The player actor session;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerActorSession extends ISession {

	/**
	 * Get player actor;
	 * 
	 * @return
	 */
	public ActorRef getPlayerActor();
}
