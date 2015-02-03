package com.stone.game.data;

import com.stone.actor.future.IDelteFuture;
import com.stone.actor.future.IInsertFuture;
import com.stone.actor.future.IQueryFuture;
import com.stone.actor.future.IUpdateFuture;
import com.stone.actor.system.IActorSystem;
import com.stone.core.data.IDataService;
import com.stone.core.entity.IEntity;
import com.stone.db.actor.DBSystemDeleteCall;
import com.stone.db.actor.DBSystemInsertCall;
import com.stone.db.actor.DBSystemQueryCall;
import com.stone.db.actor.DBSystemUpdateCall;

public class DataService implements IDataService {
	protected IActorSystem dbActorSystem;

	@SuppressWarnings("unchecked")
	@Override
	public <T> IInsertFuture<T> insert(IEntity<?> entity) {
		return (IInsertFuture<T>) dbActorSystem.putSystemCall(new DBSystemInsertCall(entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IDelteFuture<T> delete(IEntity<?> entity) {
		return (IDelteFuture<T>) dbActorSystem.putSystemCall(new DBSystemDeleteCall(entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IUpdateFuture<T> update(IEntity<?> entity) {
		return (IUpdateFuture<T>) dbActorSystem.putSystemCall(new DBSystemUpdateCall(entity));
	}

	@Override
	public <T> IQueryFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		return (IQueryFuture<T>) dbActorSystem.putSystemCall(new DBSystemQueryCall<T>(queryName, params, values));
	}


}
