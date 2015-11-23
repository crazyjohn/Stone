package com.stone.core.db.mysql;

import java.sql.SQLException;

import com.stone.core.db.service.mysql.IMysqlDBService;
import com.stone.core.db.service.mysql.MysqlDBService;

/**
 * Mysql db test;
 * <p>
 * <ul>
 * <li>Insert count: 99999, cost time: 4289388ms(71minutes)</li>
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
		// insert
		testInsert(dbService, 1, 100000);
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

}
