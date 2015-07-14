package com.stone.core.data.msg;

import com.stone.core.entity.IEntity;

public class DBUpdateMessage implements IDBMessage {
	private final IEntity entity;

	public DBUpdateMessage(IEntity entity) {
		this.entity = entity;
	}

	@Override
	public Class<?> getEntityClass() {
		return entity.getClass();
	}

	public IEntity getEntity() {
		return entity;
	}
}
