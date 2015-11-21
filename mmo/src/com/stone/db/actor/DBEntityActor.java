package com.stone.db.actor;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.data.msg.DBDeleteMessage;
import com.stone.core.data.msg.DBInsertMessage;
import com.stone.core.data.msg.DBUpdateMessage;
import com.stone.core.db.service.orm.IEntityDBService;
import com.stone.core.entity.IEntity;

/**
 * Common entity actor;
 * 
 * @author crazyjohn
 *
 */
public class DBEntityActor extends UntypedActor {
	protected final Class<?> entityClass;
	protected final IEntityDBService dbService;

	public DBEntityActor(Class<?> entityClass, IEntityDBService dbService) {
		this.entityClass = entityClass;
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBInsertMessage) {
			IEntity entity = ((DBInsertMessage)msg).getEntity();
			dbService.insert(entity);
		} else if (msg instanceof DBUpdateMessage) {
			IEntity entity = ((DBUpdateMessage)msg).getEntity();
			dbService.update(entity);
		} else if (msg instanceof DBDeleteMessage) {
			IEntity entity = ((DBDeleteMessage)msg).getEntity();
			dbService.delete(entity);
		} else {
			this.unhandled(msg);
		}
	}

	public static Props props(Class<?> entityClass, IEntityDBService dbService) {
		// no context, so create with router for balance
		return Props.create(DBEntityActor.class, entityClass, dbService);
	}

}
