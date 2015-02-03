package com.stone.db.actor;

import com.stone.actor.system.IActorSystemCall;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * Insert call;
 * 
 * @author crazyjohn
 *
 */
public class DBSystemInsertCall implements IActorSystemCall<IEntity<?>> {
	private IEntity<?> entity;

	public DBSystemInsertCall(IEntity<?> entity) {
		this.entity = entity;
	}

	@Override
	public IEntity<?> execute(IDBService dbService) {
		// TODO Auto-generated method stub
		return null;
	}

	public IEntity<?> getEntity() {
		return entity;
	}

}
