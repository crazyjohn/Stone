package com.stone.game.human;

import akka.actor.ActorRef;

import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.Player;

/**
 * 游戏角色业务对象;
 * 
 * @author crazyjohn
 *
 */
public class Human {
	/** human guid */
	private long guid;
	/** player */
	private Player player;

	public Human() {
		// init
		initModule();
	}

	private void initModule() {
		// init item module
	}

	public long getGuid() {
		return guid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Handle the system internal message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onInternalMessage(Object message, ActorRef playerActor) {
	}

	/**
	 * Handle the net external message;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 */
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) {
		// TODO Auto-generated method stub

	}

}
