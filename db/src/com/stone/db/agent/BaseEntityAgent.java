package com.stone.db.agent;

import java.io.Serializable;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

public abstract class BaseEntityAgent<E extends IEntity<?>> implements IEntityAgent<E> {
	/** bind classes */
	protected Class<?>[] bindClasses;
	/** dbService */
	protected IDBService dbService;

	@Override
	public Class<?>[] getBindedClasses() {
		return bindClasses;
	}

	@Override
	public Serializable insert(E entity) {
		return dbService.insert(entity);
	}

	@Override
	public void delete(E entity) {
		dbService.delete(entity);
	}

	@Override
	public void update(E entity) {
		dbService.update(entity);
	}

}
