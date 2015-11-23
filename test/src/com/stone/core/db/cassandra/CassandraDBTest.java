package com.stone.core.db.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.update;

import com.datastax.driver.core.ResultSet;
import com.stone.core.db.service.CassandraDBService;
import com.stone.core.db.service.cassandra.ICassandraDBService;

/**
 * The cassandra test;
 * <p>
 * localhost cassandra server:
 * <p>
 * <ul>
 * <li>130s: 100000record with primary and puid_index</li>
 * <li>116061ms: 100000record when delete puid_index</li>
 * </ul>
 * 
 * 
 * <p>
 * remote cassandra server:
 * <ul>
 * <li>
 * <li>5288495ms: 100000record</li>
 * </ul>
 * 
 * @author crazyjohn
 *
 */
public class CassandraDBTest {

	public static void main(String[] args) {
		// mac: 10.0.8.5
		// merge: 203.195.218.172
		ICassandraDBService dbService = new CassandraDBService("203.195.218.172", 0, "stone");
		// truncate table
		testTruncate(dbService);
		// insert test
		testInsert(dbService, 1, 100000);
		// close
		dbService.shutdown();
	}

	protected static void testTruncate(ICassandraDBService dbService) {
		dbService.executeCQL("truncate player");
	}

	protected static ResultSet testQueryAll(ICassandraDBService dbService) {
		long beginTime = System.currentTimeMillis();
		ResultSet rs = dbService.executeStatement(select().from("player"));
		System.out.println(String.format("Query all rows, cost time: %d", (System.currentTimeMillis() - beginTime)));
		return rs;
	}

	protected static void testDelete(ICassandraDBService dbService, int from, int to) {
		long beginTime = System.currentTimeMillis();
		for (int i = from; i <= to; i++) {
			dbService.executeStatement(delete().from("player").where(eq("id", i)));
		}
		System.out.println(String.format("Delete count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
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

	protected static void testUpdate(ICassandraDBService dbService, int from, int to) {
		long beginTime = System.currentTimeMillis();
		for (int i = from; i <= to; i++) {
			// + "')"
			dbService.executeStatement(update("player").with(set("puid", "bot" + i + "_update")).where(eq("id", i)));
		}
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
	}
}
