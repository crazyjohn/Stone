package com.stone.core.data;

import com.stone.actor.future.IActorFuture;
import com.stone.core.entity.IEntity;

/**
 * 数据层RPC服务;<br>
 * 提供给业务层来调用;
 * 
 * @author crazyjohn
 *
 */
public interface IDataService {

	public <T> IActorFuture<T> insert(IEntity<?> entity);

	public <T> IActorFuture<T> delete(IEntity<?> entity);

	public <T> IActorFuture<T> update(IEntity<?> entity);

	public <T> IActorFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values);
}
