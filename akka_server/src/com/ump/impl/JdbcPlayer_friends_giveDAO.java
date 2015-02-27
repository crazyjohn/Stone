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
import com.ump.model.Player_friends_give;

public class JdbcPlayer_friends_giveDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_friends_giveDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_friends_giveDAO instance;

	public static JdbcPlayer_friends_giveDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_friends_giveDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_friends_give getPlayer_friends_give(int playerId) {

		try {
			String sql = "select * from Player_friends_give where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_friends_give>(Player_friends_give.class));
			if (o != null) {
				return (Player_friends_give) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_friends_give not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_friends_give[] list) {
		try {
			String sql = "INSERT INTO player_friends_give " + "(player_id,friend_give_num,time) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_friends_give ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getFriend_give_num());
					ps.setLong(3, ui.getTime());
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

	public boolean upDateList(final Player_friends_give[] list) {
		try {
			String sql = "UPDATE player_friends_give SET friend_give_num=?,time=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_friends_give ui = list[i];
					ps.setInt(1, ui.getFriend_give_num());
					ps.setLong(2, ui.getTime());
					ps.setInt(3, ui.getPlayer_id());
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
