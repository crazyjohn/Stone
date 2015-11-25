package com.stone.core.db.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.stone.core.db.service.mysql.IMysqlDBService;
import com.stone.core.db.service.mysql.MysqlDBService;

/**
 * Mysql db test;
 * <p>
 * <ul>
 * <li>Insert count: 99999, cost time: 4289388ms(71minutes); (23wr/s) use
 * statement;innodb_buffer_pool_size = 128M</li>
 * <li>Insert count: 65534, cost time: 2542002ms(42minutes); (26wr/s) use
 * statement;innodb_buffer_pool_size = 1024M</li>
 * <li>Batch insert perCount: 2000, totalCount: 65534, cost time: 9803ms(9s);
 * use Preparedstatement;innodb_buffer_pool_size = 1024M</li>
 * </ul>
 * 
 * @author crazyjohn
 *
 */
public class MysqlDBTest {

	public static void main(String[] args) throws Exception {
		String host = "jdbc:mysql://localhost:3306/stone";
		String user = "root";
		String password = "";
		IMysqlDBService dbService = new MysqlDBService(host, user, password);
		// truncate
		testTruncate(dbService);
		// insert
		testPrepareInsert(dbService, 1, 65535, 2000);
	}

	protected static void testInsert(IMysqlDBService dbService, int from, int to) throws SQLException {
		long beginTime = System.currentTimeMillis();
		for (int i = from; i <= to; i++) {
			// "insert into player (id, puid) values(" + i + ", '" + ("bot" + i)
			// + "')"
			dbService.executeUpdate("insert into player (id, puid) values(" + i + ", '" + ("bot" + i) + "')");
		}
		System.out.println(String.format("Insert count: %d, cost time: %d", (to - from), (System.currentTimeMillis() - beginTime)));
	}

	protected static void testPrepareInsert(IMysqlDBService dbService, int from, int to, int batchCount) throws SQLException {
		long beginTime = System.currentTimeMillis();
		dbService.autoCommit(false);
		PreparedStatement prepare = dbService.createPreparedStatement("insert into player (id, puid) values(?, ?)");
		for (int i = from; i <= to; i++) {
			prepare.setInt(1, i);
			prepare.setString(2, "bot" + i);
			prepare.addBatch();
			if (i % batchCount == 0) {
				prepare.executeBatch();
				dbService.commit();
			}
		}
		prepare.executeBatch();
		dbService.commit();
		System.out.println(String.format("Batch insert perCount: %d, totalCount: %d, cost time: %d", batchCount, (to - from),
				(System.currentTimeMillis() - beginTime)));
	}

	protected static void testTruncate(IMysqlDBService dbService) throws SQLException {
		dbService.executeUpdate("truncate player");
	}

}
