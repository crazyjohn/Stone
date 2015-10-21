package com.stone.agent.msg.internal;

public class RegisterAgentPlayer {
	private final long playerId;

	public RegisterAgentPlayer(long playerId) {
		this.playerId = playerId;
	}

	public long getPlayerId() {
		return playerId;
	}

}
