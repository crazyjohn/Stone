package com.stone.core.msg;

import akka.actor.ActorRef;

import com.stone.core.session.BaseActorSession;

/**
 * Message from client to agent server;
 * @author crazyjohn
 *
 */
public interface CAMessage extends ISessionMessage<BaseActorSession> {

	/**
	 * Get the player actor;
	 * 
	 * @return
	 */
	public ActorRef getPlayerActor();

}
