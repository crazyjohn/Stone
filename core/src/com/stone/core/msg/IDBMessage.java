package com.stone.core.msg;

import com.stone.core.entity.IEntity;

/**
 * 数据库消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDBMessage extends IMessage {
	public Class<? extends IEntity<?>> getEntityClass();
}
