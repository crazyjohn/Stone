package com.stone.core.node.service;

import com.stone.core.lifecircle.ILifeCircle;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * The stone service;
 * 
 * @author crazyjohn
 *
 */
public interface IStoneActorService extends ILifeCircle {

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
