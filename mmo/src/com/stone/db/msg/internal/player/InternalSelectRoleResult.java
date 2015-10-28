package com.stone.db.msg.internal.player;

import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.db.entity.HumanEntity;

@PlayerInternalMessage
public class InternalSelectRoleResult {
	private final HumanEntity entity;

	public InternalSelectRoleResult(HumanEntity entity) {
		this.entity = entity;
	}

	public HumanEntity getEntity() {
		return entity;
	}

}
