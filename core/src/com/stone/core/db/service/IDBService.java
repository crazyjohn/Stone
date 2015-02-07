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
	/**
	 * 更新实体接口;
	 * 
	 * @param entity
	 */
	public void update(IEntity<?> entity);

	/**
	 * 插入实体接口;
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable insert(IEntity<?> entity);

	/**
	 * 删除实体接口;
	 * 
	 * @param entity
	 */
	public void delete(IEntity<?> entity);

	/**
	 * 根据id和类名删除实体;
	 * 
	 * @param entityClass
	 * @param id
	 */
	public void deleteById(Class<?> entityClass, Serializable id);

	/**
	 * 查询接口;
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
	 * 心跳接口;
	 */
	public void heartBeat();

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
