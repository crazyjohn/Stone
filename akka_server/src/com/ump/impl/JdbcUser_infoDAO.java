package com.ump.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.i4joy.util.Tools;
import com.mysql.jdbc.Statement;
import com.ump.model.User_info;

public class JdbcUser_infoDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcUser_infoDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public int add(User_info mi) {
		try {
			final String sqlTest = "INSERT INTO user_info (username,password) VALUES " + "('" + mi.getUsername() + "','" + mi.getPassword() + "')";
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

	public User_info getUserInfo(String username) {

		String sql = "select password,user_id from user_info where username = " + username;
		try {
			final User_info user_info = new User_info();
			getJdbcTemplate().query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					user_info.setPassword(rs.getString(1));
					user_info.setUser_id(rs.getInt(2));
				}
			});
			user_info.setUsername(username);
			return user_info;
		} catch (Exception e) {
			// Tools.printError(e, logger);
			Tools.printlnInfo("没有找到 " + username, logger);
		}
		return null;
	}

	public HashMap<String, String> getALL() {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			int offset = 5000;
			int index = 0;
			while (true) {
				String sql = "select * from user_info where user_id >= " + (index * offset) + " and user_id <= " + ((index + 1) * offset);
				ArrayList<User_info> temp = (ArrayList<User_info>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(User_info.class));
				index++;
				if (temp == null || temp.size() == 0) {
					break;
				}
				for (int i = 0; i < temp.size(); i++) {
					hm.put(temp.get(i).getUsername(), temp.get(i).getPassword());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	public boolean addList(final User_info[] list) {

		try {
			String sql = "INSERT INTO user_info " + "(username,password) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					User_info ui = list[i];
					ps.setString(1, ui.getUsername());
					ps.setString(2, ui.getPassword());
				}

				public int getBatchSize() {
					return list.length;
				}

			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean upDate(String userName, String password) {
		try {
			String sql = "UPDATE user_info SET password = ? " + " WHERE username = ?";
			int i = getJdbcTemplate().update(sql, new Object[] { password, userName });
			return i > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upDateList(final User_info[] uis) {
		try {
			// String sql = "INSERT INTO user_info " +
			// "(username,password) VALUES " + "(?,?)";
			String sql = "UPDATE user_info SET password = ? " + " WHERE username = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					User_info ui = uis[i];
					ps.setString(1, ui.getPassword());
					ps.setString(2, ui.getUsername());
				}

				public int getBatchSize() {
					return uis.length;
				}

			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
