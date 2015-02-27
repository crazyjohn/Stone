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
import com.ump.model.Player_activity_battle;
public class JdbcPlayer_activity_battleDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_activity_battleDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	public static JdbcPlayer_activity_battleDAO instance;

	public static JdbcPlayer_activity_battleDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_activity_battleDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_activity_battle getPlayer_activity_battle(int playerId) {

		try {
			String sql = "select * from player_activity_battle where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_activity_battle>(Player_activity_battle.class));
			if (o != null) {
				return (Player_activity_battle) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_activity_battle not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_activity_battle[] list) {
		try {
			String sql = "INSERT INTO player_activity_battle " + "(player_id,num,time) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_activity_battle ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getNum());
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

	public boolean upDateList(final Player_activity_battle[] list) {
		try {
			String sql = "UPDATE player_activity_battle SET num=?,time=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_activity_battle ui = list[i];
					ps.setInt(1, ui.getNum());
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
