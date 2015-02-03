package com.stone.game.data;

import com.stone.actor.future.IDelteFuture;
import com.stone.actor.future.IInsertFuture;
import com.stone.actor.future.IQueryFuture;
import com.stone.actor.future.IUpdateFuture;
import com.stone.actor.system.IActorSystem;
import com.stone.core.data.IDataService;
import com.stone.core.entity.IEntity;

public class DataService implements IDataService {
	protected IActorSystem actorSystem;

	@Override
	public <T> IInsertFuture<T> insert(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IDelteFuture<T> delete(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IUpdateFuture<T> update(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IQueryFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}


}
