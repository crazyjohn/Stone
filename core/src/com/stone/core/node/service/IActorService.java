package com.stone.core.node.service;

import com.stone.core.lifecircle.ILifeCircle;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * The actor service;
 * 
 * @author crazyjohn
 *
 */
public interface IActorService extends ILifeCircle {

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
