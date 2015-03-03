package com.stone.db.msg.internal;

import java.util.List;

import com.stone.db.entity.PlayerEntity;

public class InternalLoginResult {
	private final List<PlayerEntity> playerEntities;

	public InternalLoginResult(List<PlayerEntity> playerEntities) {
		this.playerEntities = playerEntities;
	}

	public List<PlayerEntity> getPlayerEntities() {
		return playerEntities;
	}
}
