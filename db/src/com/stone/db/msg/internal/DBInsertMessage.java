package com.stone.db.msg.internal;

import com.stone.core.entity.IEntity;

public class DBInsertMessage implements IDBOperationWithEntity {
	private final IEntity<?> entity;

	public DBInsertMessage(IEntity<?> entity) {
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
