package com.stone.core.system;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Actor sytem interface;
 * 
 * @author crazyjohn
 *
 */
public interface IActorHostSystem {

	/**
	 * Shutdown the system;
	 */
	public void shutdown();

	/**
	 * Get the real actorSystem;
	 * 
	 * @return
	 */
	public ActorSystem getSystem();

	/**
	 * Get the masterActor;
	 * 
	 * @return
	 */
	public ActorRef getMasterActor();
}
