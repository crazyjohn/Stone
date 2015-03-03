package com.stone.db.msg.internal;

public class InternalGetRoleList {
	private final long playerId;

	public InternalGetRoleList(long playerId) {
		this.playerId = playerId;
	}

	public long getPlayerId() {
		return playerId;
	}
}
