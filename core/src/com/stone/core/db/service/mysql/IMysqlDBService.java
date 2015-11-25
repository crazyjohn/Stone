package com.stone.core.db.service.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMysqlDBService {

	public ResultSet executeQuery(String sql) throws SQLException;

	public int executeUpdate(String sql) throws SQLException;

	public PreparedStatement createPreparedStatement(String sql) throws SQLException;
	
	public void autoCommit(boolean flag) throws SQLException;
	
	public void commit() throws SQLException;

}
