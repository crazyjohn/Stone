package com.stone.db.msg;

import com.stone.core.entity.IEntity;
import com.stone.core.msg.IDBCallbackMessage;
import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.SystemInternalMessage;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;

public class DBInsertMessage extends SystemInternalMessage implements
		IDBMessage {

	private IEntity<?> entity;

	public DBInsertMessage(IEntity<?> entity) {
		this.entity = (entity);
	}

	@Override
	public IMessageProcessor getProcessor(IDispatcher myDispatcher) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends IEntity<?>> getEntityClass() {
		return (Class<? extends IEntity<?>>) entity.getClass();
	}

	@Override
	public IDBCallbackMessage executeSelf() {
		// TODO Auto-generated method stub
		return null;
	}

	public IEntity<?> getEntity() {
		return entity;
	}

}
