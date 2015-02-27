package com.ump.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.i4joy.util.Tools;
import com.mysql.jdbc.Statement;
import com.ump.model.Servers;

public class JdbcServersDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcServersDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public int add(Servers mi) {
		try {
			final String sqlTest = "INSERT INTO servers (server_name,status,online_num) VALUES " + "('" + mi.getServer_name() + "','" + mi.getStatus() + "'," + mi.getOnline_num() + ")";
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sqlTest, Statement.RETURN_GENERATED_KEYS);
					return ps;
				}

			}, keyHolder);
			long id = keyHolder.getKey().longValue();
			return (int) id;
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return 0;
	}

	public ArrayList<Servers> getList() {

		try {
			String sql = "select * from servers";
			return (ArrayList<Servers>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Servers.class));
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return null;
	}
	
	public void upDateList(final Servers[] servers)
	{
		try {
			String sql = "UPDATE servers SET online_num = ? WHERE server_name = ?";
			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							Servers ms = servers[i];
							ps.setInt(1, ms.getOnline_num());
							ps.setString(2, ms.getStatus());
						}

						public int getBatchSize() {
							return servers.length;
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args)
//	{
//		double m = 139123775977634d;
//		double n =  14d;
//		double d = java.lang.StrictMath.pow(m,1.0/n);
//		System.out.println(d);
////		long l= 6;
////		for(int i = 0; i < 13; i++)
////		{
////			l = l*l;
////			System.out.println(l);
////		}
////		System.out.println(l);
//	}
	
}
