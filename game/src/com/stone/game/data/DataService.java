package com.stone.game.data;

import com.stone.actor.future.IActorFuture;
import com.stone.actor.system.IActorSystem;
import com.stone.core.data.IDataService;
import com.stone.core.entity.IEntity;
import com.stone.db.DBActorSystem;
import com.stone.db.actor.DBSystemDeleteCall;
import com.stone.db.actor.DBSystemInsertCall;
import com.stone.db.actor.DBSystemQueryCall;
import com.stone.db.actor.DBSystemUpdateCall;

public class DataService implements IDataService {
	protected IActorSystem dbActorSystem = DBActorSystem.getInstance();

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> insert(IEntity<?> entity) {
		return  (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemInsertCall(entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> delete(IEntity<?> entity) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemDeleteCall(entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> update(IEntity<?> entity) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemUpdateCall(entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemQueryCall<T>(queryName, params, values));
	}

}
