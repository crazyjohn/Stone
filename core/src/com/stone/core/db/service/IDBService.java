package com.stone.core.db.service;

import com.stone.core.entity.IEntity;

/**
 * 数据服务接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDBService {
	public void update(IEntity<?> entity, boolean async);
}
