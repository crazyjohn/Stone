package com.stone.core.db.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.update;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.stone.core.db.service.CassandraDBService;
import com.stone.core.db.service.cassandra.ICassandraDBService;

/**
 * The cassandra test;
 * <p>
 * localhost cassandra server:
 * <p>
 * <ul>
 * <li>130s: 100000write with primary and puid_index;(769wr/s) use Statement</li>
 * <li>116s: 100000write when delete puid_index;(862wr/s) use Statement</li>
 * <li>10s: 10000write when delete puid_index;(1000wr/s) use PreparedStatement</li>
 * <li>Insert count: 65534, cost time: 1193; use BatchStatement</li>
 * </ul>
 * 
 * 
 * <p>
 * remote cassandra server:
 * <ul>
 * <li>
 * <li>5288495ms: 100000record(88minutes)</li>
 * </ul>
 * 
 * @author crazyjohn
 *
 */
public class CassandraDBTest {

	public static void main(String[] args) {
		// mac: 10.0.8.5
		// merge: 203.195.218.172
		ICassandraDBService dbService = new CassandraDBService("127.0.0.1", 9042, "stone");
		// truncate table
		testTruncate(dbService);
		// insert test
		testBatchPrepareInsert(dbService, 1, 65535);
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

	protected static void testPrepareInsert(ICassandraDBService dbService, int from, int to) {
		long beginTime = System.currentTimeMillis();
		PreparedStatement statement = dbService.prepare("insert into player (id, puid) values(?, ?)");
		BoundStatement bound = new BoundStatement(statement);
		for (int i = from; i <= to; i++) {
			dbService.executeStatement(bound.bind(i, "bot" + i));
		}
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
	}

	/**
	 * Batch operation;
	 * 
	 * @param dbService
	 * @param from
	 * @param to
	 */
	protected static void testBatchPrepareInsert(ICassandraDBService dbService, int from, int to) {
		long beginTime = System.currentTimeMillis();
		PreparedStatement statement = dbService.prepare("insert into player (id, puid) values(?, ?)");
		BatchStatement batch = new BatchStatement();
		// BoundStatement bound = new BoundStatement(statement);
		for (int i = from; i <= to; i++) {
			batch.add(statement.bind(i, "bot" + i));
		}
		System.out.println("Batch size: " + batch.size());
		// execute batch
		dbService.executeStatement(batch);
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
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
