package com.stone.aop.db;

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
	public void update(IEntity<?> entity, boolean async) {
		logger.info(String.format("Update entity name: %s, id: %s, async: %s", entity
				.getClass().getSimpleName(), entity.getId(), async));
	}

}
