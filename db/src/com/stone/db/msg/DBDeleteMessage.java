package com.stone.db.msg;

import com.stone.core.entity.IEntity;
import com.stone.core.msg.IDBCallbackMessage;
import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.SystemInternalMessage;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;

public class DBDeleteMessage extends SystemInternalMessage implements
		IDBMessage {

	@Override
	public IMessageProcessor getProcessor(IDispatcher myDispatcher) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends IEntity<?>> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDBCallbackMessage executeSelf() {
		// TODO Auto-generated method stub
		return null;
	}

}
