package com.stone.db.msg.internal;

import com.stone.core.entity.IEntity;

public class DBUpdateMessage implements IDBOperationWithEntity {
	private final IEntity<?> entity;

	public DBUpdateMessage(IEntity<?> entity) {
		this.entity = entity;
	}

	@Override
	public Class<?> getEntityClass() {
		return entity.getClass();
	}

	public IEntity<?> getEntity() {
		return entity;
	}
}
