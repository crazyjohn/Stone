package com.stone.db.msg.internal;

import java.util.List;

import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.db.entity.HumanEntity;

@PlayerInternalMessage
public class InternalGetRoleListResult {
	private final List<HumanEntity> humanEntities;

	public InternalGetRoleListResult(List<HumanEntity> humanEntities) {
		this.humanEntities = humanEntities;
	}

	public List<HumanEntity> getHumanEntities() {
		return humanEntities;
	}
}
