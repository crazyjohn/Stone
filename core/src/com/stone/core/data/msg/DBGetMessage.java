package com.stone.core.data.msg;

import java.io.Serializable;

public class DBGetMessage implements IDBMessage {
	private final long id;
	private final Class<?> entityClass;

	public DBGetMessage(long id, Class<?> entityClass) {
		this.id = id;
		this.entityClass = entityClass;
	}

	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Serializable getId() {
		return id;
	}

}
