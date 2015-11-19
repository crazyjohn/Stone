package com.stone.core.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.stone.core.db.service.CassandraDBService;
import com.stone.core.db.service.cassandra.ICassandraDBService;

/**
 * The cassandra test;
 * 
 * @author crazyjohn
 *
 */
public class CassandraDBTest {

	public static void main(String[] args) {
		ICassandraDBService dbService = new CassandraDBService("127.0.0.1", 0, "stone");
		dbService.heartBeat();
		ResultSet rs = dbService.executeCQL("select * from player");
		// all rows
		for (Row eachRow : rs.all()) {
			System.out.println(eachRow.getInt("id") + ", " + eachRow.getString("puid"));
		}
		// insert 100000000
		int from = 0;
		int to = 100000000;
		long beginTime = System.currentTimeMillis();
		for (int i = from; i <= to; i++) {
			dbService.executeCQL("insert into player (id, puid) values(" + i + ", '" + ("bot" + i) + "')");
		}
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
		
		// // delete
		// for (int i = from; i <= to; i++) {
		// dbService.executeCQL("delete from player where id = " + i);
		// }
	}
}
