package com.stone.game.session;

import akka.actor.ActorRef;

import com.stone.core.session.ISession;

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
