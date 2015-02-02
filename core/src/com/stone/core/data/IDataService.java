package com.stone.core.data;

import java.util.List;

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

	public <T> void insert(IEntity<?> entity, IDBCallback<T> callback);

	public void delete(IEntity<?> entity);

	public void update(IEntity<?> entity);

	public <T> void queryByNameAndParams(String queryName, String[] params, Object[] values, IDBCallback<T> callback);

	public IActorFuture<List<IEntity<?>>> queryByNameAndParams(String queryPlayerByNameAndPassword, String[] strings, Object[] objects);
}
