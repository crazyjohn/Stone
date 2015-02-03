package com.stone.core.data;

import com.stone.actor.future.IDelteFuture;
import com.stone.actor.future.IInsertFuture;
import com.stone.actor.future.IQueryFuture;
import com.stone.actor.future.IUpdateFuture;
import com.stone.core.entity.IEntity;

/**
 * 数据层RPC服务;<br>
 * 提供给业务层来调用;
 * 
 * @author crazyjohn
 *
 */
public interface IDataService {

	public <T> IInsertFuture<T> insert(IEntity<?> entity);

	public <T> IDelteFuture<T> delete(IEntity<?> entity);

	public <T> IUpdateFuture<T> update(IEntity<?> entity);

	public <T> IQueryFuture<T> queryByNameAndParams(String queryName, String[] params, Object[] values);
}
