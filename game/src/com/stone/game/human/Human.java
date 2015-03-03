package com.stone.game.human;

import akka.actor.ActorRef;

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
	 * Fire message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onMessage(Object message, ActorRef playerActor) {
	}

}
