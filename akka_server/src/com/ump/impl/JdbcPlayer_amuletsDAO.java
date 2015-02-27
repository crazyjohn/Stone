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
import com.ump.model.Player_amulets;

public class JdbcPlayer_amuletsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayer_amuletsDAO instance;

	public static JdbcPlayer_amuletsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_amuletsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcPlayer_amuletsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public Player_amulets getPlayer_amulets(int playerId) {

		try {
			String sql = "select * from player_amulets where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_amulets>(Player_amulets.class));
			if (o != null) {
				return (Player_amulets) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("player_amulets not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_amulets[] list) {
		try {
			String sql = "INSERT INTO player_amulets " + "(player_id,amulet_max_num,amulet_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_amulets ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getAmulet_max_num());
					ps.setString(3, ui.getAmulet_json());
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

	public boolean upDateList(final Player_amulets[] list) {
		try {
			String sql = "UPDATE player_amulets SET amulet_max_num=?,amulet_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_amulets ui = list[i];
					ps.setInt(1, ui.getAmulet_max_num());
					ps.setString(2, ui.getAmulet_json());
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
