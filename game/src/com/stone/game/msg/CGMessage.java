package com.stone.game.msg;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.session.GamePlayerSession;

/**
 * client和GameServer通信消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface CGMessage extends ISessionMessage<GamePlayerSession> {

	/**
	 * Get the player actor;
	 * 
	 * @return
	 */
	public ActorRef getPlayerActor();

}
