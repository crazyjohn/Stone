package com.stone.actor.system;

import com.stone.core.db.service.IDBService;

public interface IActorSystemCall<T> {

	public T execute(IDBService dbService);

}
