package com.stone.core.db.service.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDBService implements IMysqlDBService {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Can't find jdbc driver!");
		}
	}

	private Connection connection;
	private Statement statement;

	public MysqlDBService(String host, String user, String password) {
		try {
			connection = DriverManager.getConnection(host, user, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException("Init mysql error!", e);
		}
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}

}
