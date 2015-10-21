package com.stone.agent.msg.internal;

public class UnRegisterAgentPlayer {
	private final long playerId;

	public UnRegisterAgentPlayer(long playerId) {
		this.playerId = playerId;
	}

	public long getPlayerId() {
		return playerId;
	}
}
