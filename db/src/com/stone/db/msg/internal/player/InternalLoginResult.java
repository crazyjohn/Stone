package com.stone.db.msg.internal.player;

import java.util.List;

import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.db.entity.PlayerEntity;

@PlayerInternalMessage
public class InternalLoginResult {
	private final List<PlayerEntity> playerEntities;

	public InternalLoginResult(List<PlayerEntity> playerEntities) {
		this.playerEntities = playerEntities;
	}

	public List<PlayerEntity> getPlayerEntities() {
		return playerEntities;
	}
}
