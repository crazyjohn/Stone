package com.stone.db.msg.internal;

import com.stone.proto.Auths.SelectRole;
import com.stone.proto.Auths.SelectRole.Builder;

public class InternalSelectRole {
	private final long playerId;
	private final SelectRole.Builder builder;

	public InternalSelectRole(long playerId, Builder builder) {
		this.playerId = playerId;
		this.builder = builder;
	}

	public SelectRole.Builder getBuilder() {
		return builder;
	}

	public long getPlayerId() {
		return playerId;
	}
}
