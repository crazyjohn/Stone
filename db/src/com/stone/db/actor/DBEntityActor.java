package com.stone.db.actor;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;
import com.stone.db.msg.internal.DBDeleteMessage;
import com.stone.db.msg.internal.DBInsertMessage;
import com.stone.db.msg.internal.DBUpdateMessage;

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
		if (msg instanceof DBInsertMessage) {
			IEntity<?> entity = ((DBInsertMessage)msg).getEntity();
			dbService.insert(entity);
		} else if (msg instanceof DBUpdateMessage) {
			IEntity<?> entity = ((DBUpdateMessage)msg).getEntity();
			dbService.update(entity);
		} else if (msg instanceof DBDeleteMessage) {
			IEntity<?> entity = ((DBDeleteMessage)msg).getEntity();
			dbService.delete(entity);
		} else {
			this.unhandled(msg);
		}
	}

	public static Props props(Class<?> entityClass, IDBService dbService) {
		// no context, so create with router for balance
		return Props.create(DBEntityActor.class, entityClass, dbService);
	}

}
