package com.stone.game.player;

import com.stone.core.msg.MessageParseException;
import com.stone.game.msg.ProtobufMessage;

import akka.actor.ActorRef;

/**
 * The player module;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerModule {

	/**
	 * Get the player;
	 * 
	 * @return
	 */
	public Player getPlayer();

	/**
	 * Handle the system internal message;
	 * 
	 * @param msg
	 * @param playerActor
	 */
	public void onInternalMessage(Object msg, ActorRef playerActor);

	/**
	 * Handle the net(io) external message;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 * @throws MessageParseException
	 */
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException;
}
