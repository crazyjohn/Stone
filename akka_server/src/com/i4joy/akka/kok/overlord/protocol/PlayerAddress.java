package com.i4joy.akka.kok.overlord.protocol;

import java.io.Serializable;

public class PlayerAddress implements Serializable {
	public PlayerAddress() {

	}

	private int playerId;
	private String playerGroupName;
	private String playerName;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerGroupName() {
		return playerGroupName;
	}

	public void setPlayerGroupName(String playerGroupName) {
		this.playerGroupName = playerGroupName;
	}

	public int getPlayerId() {
		return playerId;
	}

}
