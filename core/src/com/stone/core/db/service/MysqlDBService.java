package com.stone.core.db.service;

import java.io.Serializable;
import java.util.List;

import com.stone.core.entity.IEntity;

/**
 * MySql数据服务;
 * 
 * @author crazyjohn
 *
 */
public class MysqlDBService implements IDBService {

	@Override
	public void update(IEntity<?> entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable insert(IEntity<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IEntity<?> entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> queryByNameAndParams(String queryName, String[] params, Object[] values, int maxResult, int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

}
