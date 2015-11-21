package com.stone.core.db.service.orm;

import java.io.Serializable;
import java.util.List;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * The db service;
 * 
 * @author crazyjohn
 *
 */
public interface IEntityDBService extends IDBService {
	/**
	 * Update the db entity;
	 * 
	 * @param entity
	 */
	public void update(IEntity entity);

	/**
	 * Get the entity;
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public IEntity get(Class<?> entityClass, Serializable id);

	/**
	 * Insert;
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable insert(IEntity entity);

	/**
	 * Delete;
	 * 
	 * @param entity
	 */
	public void delete(IEntity entity);

	/**
	 * Delete by id;
	 * 
	 * @param entityClass
	 * @param id
	 */
	public void deleteById(Class<?> entityClass, Serializable id);

	/**
	 * Query by queryName and params;
	 * 
	 * @param queryName
	 * @param params
	 * @param values
	 * @param maxResult
	 * @param start
	 * @return
	 */
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values, int maxResult, int start);

	/**
	 * Query;
	 * 
	 * @param queryName
	 * @param params
	 * @param values
	 * @return
	 */
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values);
}
