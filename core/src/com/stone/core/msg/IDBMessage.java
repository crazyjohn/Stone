package com.stone.core.msg;

import com.stone.core.entity.IEntity;
import com.stone.core.processor.IDispatchable;

/**
 * DB message interface;
 * 
 * @author crazyjohn
 *
 */
public interface IDBMessage extends IMessage, IDispatchable {
	/**
	 * get entity class;
	 * 
	 * @return
	 */
	public Class<? extends IEntity<?>> getEntityClass();

	public IDBCallbackMessage executeSelf();
}
