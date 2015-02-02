package com.stone.core.data;

import com.stone.actor.system.IActorSystemFuture;
import com.stone.core.entity.IEntity;

/**
 * 数据层RPC服务;<br>
 * 提供给业务层来调用;
 * 
 * @author crazyjohn
 *
 */
public interface IDataService {

	public <T> void insert(IEntity<?> entity);

	public void delete(IEntity<?> entity);

	public void update(IEntity<?> entity);

	public <T> IActorSystemFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values);
}
