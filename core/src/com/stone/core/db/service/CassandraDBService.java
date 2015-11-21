package com.stone.core.db.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.stone.core.db.service.cassandra.ICassandraDBService;

public class CassandraDBService implements ICassandraDBService {
	private Cluster cluster;
	protected Session session;

	public CassandraDBService(String host, int port, String database) {
		cluster = Cluster.builder().addContactPoint(host).build();
		session = cluster.connect(database);
	}




	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultSet executeCQL(String cql) {
		return session.execute(cql);
	}

	@Override
	public ResultSet executeStatement(Statement statement) {
		return session.execute(statement);
	}




	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}


}
