package com.stone.db.msg.system;

import java.util.List;

import com.stone.db.entity.PlayerEntity;

public class SystemLoginResult {
	private final List<PlayerEntity> playerEntities;

	public SystemLoginResult(List<PlayerEntity> playerEntities) {
		this.playerEntities = playerEntities;
	}

	public List<PlayerEntity> getPlayerEntities() {
		return playerEntities;
	}
}
