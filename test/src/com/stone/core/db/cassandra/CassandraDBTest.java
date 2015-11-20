package com.stone.core.db.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;

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
		// mac: 10.0.8.5
		ICassandraDBService dbService = new CassandraDBService("127.0.0.1", 0, "stone");

		// query all
		for (Row eachRow : testQueryAll(dbService)) {
			System.out.println(eachRow.getInt("id") + ", " + eachRow.getString("puid"));
		}
		// insert test
		// int from = 0;
		// int to = 100000000;
		// testInsert(dbService, from, to);
		// delete test
		// testDelete(dbService, from, to);
	}

	protected static ResultSet testQueryAll(ICassandraDBService dbService) {
		long beginTime = System.currentTimeMillis();
		ResultSet rs = dbService.executeStatement(select().from("player"));
		System.out.println(String.format("Query all rows, cost time: %d", (System.currentTimeMillis() - beginTime)));
		return rs;
	}

	protected static void testDelete(ICassandraDBService dbService, int from, int to) {
		for (int i = from; i <= to; i++) {
			dbService.executeStatement(delete().from("player").where(eq("id", i)));
		}
	}

	protected static void testInsert(ICassandraDBService dbService, int from, int to) {
		long beginTime = System.currentTimeMillis();
		for (int i = from; i <= to; i++) {
			// "insert into player (id, puid) values(" + i + ", '" + ("bot" + i)
			// + "')"
			dbService.executeStatement(insertInto("player").value("id", i).value("puid", "bot" + i));
		}
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
	}
}
