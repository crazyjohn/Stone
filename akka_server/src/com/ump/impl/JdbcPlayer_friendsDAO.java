package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.i4joy.util.Tools;
import com.ump.model.Player_friends;

public class JdbcPlayer_friendsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_friendsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_friendsDAO instance;

	public static JdbcPlayer_friendsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_friendsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_friends getPlayer_friends(int playerId) {

		try {
			String sql = "select * from player_friends where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_friends>(Player_friends.class));
			if (o != null) {
				return (Player_friends) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_friends not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_friends[] list) {
		try {
			String sql = "INSERT INTO player_friends " + "(player_id, friend_json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_friends ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getFriend_json());
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

	public boolean upDateList(final Player_friends[] list) {
		try {
			String sql = "UPDATE player_friends SET friend_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_friends ui = list[i];
					ps.setString(1, ui.getFriend_json());
					ps.setInt(2, ui.getPlayer_id());
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
