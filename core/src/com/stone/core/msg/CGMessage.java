package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.BaseActorSession;

/**
 * client和GameServer通信消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface CGMessage extends ISessionMessage<BaseActorSession> {

	/**
	 * Get the player actor;
	 * 
	 * @return
	 */
	public ActorRef getPlayerActor();

}
