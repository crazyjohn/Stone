package com.stone.db.actor;

import com.stone.actor.system.IActorSystemCall;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

public class DBSystemUpdateCall implements IActorSystemCall<IEntity<?>> {

	private IEntity<?> entity;

	public DBSystemUpdateCall(IEntity<?> entity) {
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
