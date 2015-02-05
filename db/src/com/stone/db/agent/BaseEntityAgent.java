package com.stone.db.agent;

import java.io.Serializable;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

public abstract class BaseEntityAgent implements IEntityAgent {
	/** bind classes */
	protected Class<?>[] bindClasses;
	/** dbService */
	protected IDBService dbService;

	@Override
	public Class<?>[] getBindedClasses() {
		return bindClasses;
	}

	@Override
	public Serializable insert(IEntity<?> entity) {
		return dbService.insert(entity);
	}

	@Override
	public void delete(IEntity<?> entity) {
		dbService.delete(entity);
	}

	@Override
	public void update(IEntity<?> entity) {
		dbService.update(entity);
	}

}
