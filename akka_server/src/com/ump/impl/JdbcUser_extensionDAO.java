package com.ump.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.util.Tools;
import com.ump.model.User_extension;

public class JdbcUser_extensionDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcUser_extensionDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public User_extension getUser_extension(String username) {

		try {
			String sql = "select * from user_extension where username = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { username }, new BeanPropertyRowMapper<User_extension>(User_extension.class));
			if (o != null) {
				return (User_extension) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("User_extension not found " + username, logger);
		}
		return null;

	}

	public boolean addList(final User_extension[] list) {
		try {
			String sql = "INSERT INTO user_extension " + "(username,mobile_id,email,client_ip,client_id,create_date,last_login_date,channel,device) VALUES " + "(?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					User_extension ui = list[i];
					ps.setString(1, ui.getUsername());
					ps.setLong(2, ui.getMobile_id());
					ps.setString(3, ui.getEmail());
					ps.setLong(4, ui.getClient_ip());
					ps.setString(5, ui.getClient_id());
					ps.setTimestamp(6, new Timestamp(ui.getCreate_date().getTime()));
					ps.setTimestamp(7, new Timestamp(ui.getLast_login_date().getTime()));
					ps.setString(8, ui.getChannel());
					ps.setString(9, ui.getDevice());

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

	public boolean upDateList(final User_extension[] list) {
		try {
			String sql = "UPDATE user_extension SET mobile_id=?,email=?,client_ip = ?,client_id = ?,create_date = ?,last_login_date = ?,channel = ?,device = ?" + " WHERE username = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					User_extension ui = list[i];
					ps.setLong(1, ui.getMobile_id());
					ps.setString(2, ui.getEmail());
					ps.setLong(3, ui.getClient_ip());
					ps.setString(4, ui.getClient_id());
					ps.setDate(5, new Date(ui.getCreate_date().getTime()));
					ps.setDate(6, new Date(ui.getLast_login_date().getTime()));
					ps.setString(7, ui.getChannel());
					ps.setString(8, ui.getDevice());
					ps.setString(9, ui.getUsername());
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
}
