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
import com.ump.model.Player_gift_checkin;

public class JdbcPlayer_gift_checkinDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_gift_checkinDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_gift_checkinDAO instance;

	public static JdbcPlayer_gift_checkinDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_gift_checkinDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_gift_checkin getPlayer_gift_checkin(int playerId) {

		try {
			String sql = "select * from player_gift_checkin where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_gift_checkin>(Player_gift_checkin.class));
			if (o != null) {
				return (Player_gift_checkin) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_gift_checkin not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_gift_checkin[] list) {
		try {
			String sql = "INSERT INTO player_gift_checkin " + "(player_id, json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_gift_checkin ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getJson());
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

	public boolean upDateList(final Player_gift_checkin[] list) {
		try {
			String sql = "UPDATE player_gift_checkin SET friend_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_gift_checkin ui = list[i];
					ps.setString(1, ui.getJson());
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