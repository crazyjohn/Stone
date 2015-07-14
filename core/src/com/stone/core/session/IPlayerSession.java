package com.stone.core.session;

import akka.actor.ActorRef;

/**
 * 玩家回话信息接口;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerSession extends ISession {

	/**
	 * Get player actor;
	 * 
	 * @return
	 */
	public ActorRef getPlayerActor();
}
