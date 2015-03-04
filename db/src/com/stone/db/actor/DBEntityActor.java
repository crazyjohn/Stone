package com.stone.db.actor;

import com.stone.core.db.service.IDBService;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

/**
 * Common entity actor;
 * 
 * @author crazyjohn
 *
 */
public class DBEntityActor extends UntypedActor {
	protected final Class<?> entityClass;
	protected final IDBService dbService;
	/** default login router count */
	private static final int DEFAULT_ROUTER_COUNT = 10;

	public DBEntityActor(Class<?> entityClass, IDBService dbService) {
		this.entityClass = entityClass;
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// TODO Auto-generated method stub

	}

	public static Props props(Class<?> entityClass, IDBService dbService) {
		// no context, so create with router for balance
		return Props.create(DBEntityActor.class, entityClass, dbService).withRouter(new RoundRobinRouter(DEFAULT_ROUTER_COUNT));
	}

}
