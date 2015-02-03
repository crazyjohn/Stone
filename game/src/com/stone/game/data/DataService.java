package com.stone.game.data;

import com.stone.actor.IActor;
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
	public <T> IActorFuture<T> insert(IActor caller, IEntity<?> entity) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemInsertCall(caller.getHostSystem(), caller.getActorId(), entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> delete(IActor caller, IEntity<?> entity) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemDeleteCall(caller.getHostSystem(), caller.getActorId(), entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> update(IActor caller, IEntity<?> entity) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemUpdateCall(caller.getHostSystem(), caller.getActorId(), entity));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IActorFuture<T> queryByNameAndParams(IActor caller, String queryName, String[] params, Object[] values) {
		return (IActorFuture<T>) dbActorSystem.putSystemCall(new DBSystemQueryCall<T>(caller.getHostSystem(), caller.getActorId(), queryName, params, values));
	}

}
