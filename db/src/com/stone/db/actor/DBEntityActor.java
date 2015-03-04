package com.stone.db.actor;

import com.stone.core.db.service.IDBService;

import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Common entity actor;
 * 
 * @author crazyjohn
 *
 */
public class DBEntityActor extends UntypedActor {
	protected final Class<?> entityClass;
	protected final IDBService dbService;

	public DBEntityActor(Class<?> entityClass, IDBService dbService) {
		this.entityClass = entityClass;
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// TODO Auto-generated method stub

	}

	public static Props props(Class<?> entityClass, IDBService dbService) {
		return Props.create(DBEntityActor.class, entityClass, dbService);
	}

}
