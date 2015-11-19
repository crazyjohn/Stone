package com.stone.core.db.service;

import java.io.Serializable;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.stone.core.db.service.cassandra.ICassandraDBService;
import com.stone.core.entity.IEntity;

public class CassandraDBService implements ICassandraDBService {
	private Cluster cluster;
	protected Session session;

	public CassandraDBService(String host, int port, String database) {
		cluster = Cluster.builder().addContactPoint(host).build();
		session = cluster.connect(database);
	}

	@Override
	public void update(IEntity entity) {

	}

	@Override
	public IEntity get(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable insert(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values, int maxResult, int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> List<T> queryByNameAndParams(String queryName, String[] params, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet executeCQL(String cql) {
		return session.execute(cql);
	}

}
