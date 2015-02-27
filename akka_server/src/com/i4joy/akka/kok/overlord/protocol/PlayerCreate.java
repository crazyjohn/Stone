package com.i4joy.akka.kok.overlord.protocol;

import java.io.Serializable;

public class PlayerCreate implements Serializable {
	public PlayerCreate() {

	}

	private int playerId;
	private String playerName;
	private int serverId;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
