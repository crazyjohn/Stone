package com.stone.core.db.cassandra;

import com.stone.core.db.service.IDBService;

public class CassandraDBTest {

	public static void main(String[] args) {
		IDBService dbService = new CassandraDBService("127.0.0.1", 0, "stone");
		dbService.heartBeat();
	}

}
