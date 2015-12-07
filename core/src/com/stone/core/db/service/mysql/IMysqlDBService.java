package com.stone.core.db.service.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.stone.core.db.service.IDBService;

/**
 * The mysql db service;
 * 
 * @author crazyjohn
 *
 */
public interface IMysqlDBService extends IDBService {

	/**
	 * Execute the query;
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException;

	/**
	 * Execute the update;
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws SQLException;

	/**
	 * Create the prepared statement;
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement createPreparedStatement(String sql) throws SQLException;

	/**
	 * Set autocomit;
	 * 
	 * @param flag
	 * @throws SQLException
	 */
	public void setAutoCommit(boolean flag) throws SQLException;

	/**
	 * Do commit;
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException;

}
