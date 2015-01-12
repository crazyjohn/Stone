package com.stone.aop.db;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * MockDBService;
 * 
 * @author crazyjohn
 *
 */
public class MockDBService implements IDBService {
	private static IDBService instance;
	private Logger logger = LoggerFactory.getLogger(MockDBService.class);

	private MockDBService() {
	}

	public static IDBService getInstance() {
		if (instance == null) {
			instance = new MockDBService();
		}
		return instance;
	}

	@Override
	public void update(IEntity<?> entity) {
		logger.info(String.format("Update entity name: %s, id: %s, async: %s",
				entity.getClass().getSimpleName(), entity.getId()));
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
	public List<Object> queryByNameAndParams(String queryName, String[] params,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub
		
	}

}
