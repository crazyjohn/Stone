package com.stone.core.data.msg;

import java.io.Serializable;

import com.stone.core.entity.IEntity;

public class DBInsertMessage implements IDBMessage, IDBEntityMessage {
	private final IEntity entity;

	public DBInsertMessage(IEntity entity) {
		this.entity = entity;
	}

	@Override
	public Class<?> getEntityClass() {
		return entity.getClass();
	}

	@Override
	public IEntity getEntity() {
		return entity;
	}
	
	@Override
	public Serializable getId() {
		return entity.getId();
	}

}
