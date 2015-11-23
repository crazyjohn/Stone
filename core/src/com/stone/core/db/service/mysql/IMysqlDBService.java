package com.stone.core.db.service.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMysqlDBService {

	public ResultSet executeQuery(String sql) throws SQLException;
	
	public int executeUpdate(String sql) throws SQLException;

}
