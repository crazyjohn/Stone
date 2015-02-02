package com.stone.game.data;

import com.stone.actor.system.IActorSystem;
import com.stone.actor.system.IActorSystemFuture;
import com.stone.core.data.IDataService;
import com.stone.core.entity.IEntity;
import com.stone.db.actor.DBSystemDelete;
import com.stone.db.actor.DBSystemInsert;
import com.stone.db.actor.DBSystemQuery;
import com.stone.db.actor.DBSystemUpdate;

public class DataService implements IDataService {
	private IActorSystem actorSystem;

	@Override
	public <T> void insert(IEntity<?> entity) {
		actorSystem.putSystemCall(new DBSystemInsert(entity));
	}

	@Override
	public void delete(IEntity<?> entity) {
		actorSystem.putSystemCall(new DBSystemDelete(entity));
	}

	@Override
	public void update(IEntity<?> entity) {
		actorSystem.putSystemCall(new DBSystemUpdate(entity));
	}

	@Override
	public <T> IActorSystemFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		return actorSystem.putSystemCall(new DBSystemQuery<T>(queryName, params, values));
	}

}
