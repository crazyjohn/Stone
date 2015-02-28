package com.stone.core.akka.game;

/**
 * Real game player;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayer {
	private final String name;

	public GamePlayer(String name) {
		this.name = name;
	}

	public void doAnyFuckingThings() {
		System.out.println("Call method: doAnyFuckingThings");
	}

	public String getName() {
		return name;
	}

}
