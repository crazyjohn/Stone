package com.stone.db.msg.internal;

import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.CreateRole.Builder;

public class InternalCreateRole {
	private final long playerId;
	private final CreateRole.Builder builder;

	public InternalCreateRole(long playerId, Builder builder) {
		this.playerId = playerId;
		this.builder = builder;
	}

	public CreateRole.Builder getBuilder() {
		return builder;
	}

	public long getPlayerId() {
		return playerId;
	}
}
