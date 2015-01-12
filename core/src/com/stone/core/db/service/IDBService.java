package com.stone.core.db.service;

import java.io.Serializable;
import java.util.List;

import com.stone.core.entity.IEntity;

/**
 * 数据服务接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDBService {

	public void update(IEntity<?> entity);

	public Serializable insert(IEntity<?> entity);

	public void delete(IEntity<?> entity);

	public void deleteById(Class<?> entityClass, Serializable id);

	public List<Object> queryByNameAndParams(String queryName, String[] params,
			Object[] values);

	public void heartBeat();
}
