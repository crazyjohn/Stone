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
import com.ump.model.Player_nomal_battle;

public class JdbcPlayer_nomal_battleDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_nomal_battleDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public static JdbcPlayer_nomal_battleDAO instance;

	public static JdbcPlayer_nomal_battleDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_nomal_battleDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_nomal_battle getPlayer_nomal_battle(int playerId) {

		try {
			String sql = "select * from player_nomal_battle where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_nomal_battle>(Player_nomal_battle.class));
			if (o != null) {
				return (Player_nomal_battle) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_nomal_battle not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_nomal_battle[] list) {
		try {
			String sql = "INSERT INTO player_nomal_battle " + "(player_id, json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_nomal_battle ui = list[i];
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

	public boolean upDateList(final Player_nomal_battle[] list) {
		try {
			String sql = "UPDATE player_nomal_battle SET json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_nomal_battle ui = list[i];
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
