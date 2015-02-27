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
import com.ump.model.Player_gold;

public class JdbcPlayer_goldDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayer_goldDAO instance;

	public static JdbcPlayer_goldDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_goldDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcPlayer_goldDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public Player_gold getPlayerGold(int playerId) {
		try {
			String sql = "select * from player_gold where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Player_gold) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_gold not found " + playerId, logger);
		}
		return null;
	}

	public boolean addList(final Player_gold[] list) {
		try {
			String sql = "INSERT INTO player_gold " + "(player_gold_record,gold,first,player_id) VALUES " + "(?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_gold ui = list[i];
					ps.setInt(1, ui.getPlayer_gold_record());
					ps.setInt(2, ui.getGold());
					ps.setByte(3, ui.getFirst());
					ps.setInt(4, ui.getPlayer_id());
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

	public boolean upDateList(final Player_gold[] list) {
		try {
			String sql = "UPDATE player_gold SET player_gold_record=?,gold=?, first=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_gold ui = list[i];
					ps.setInt(1, ui.getPlayer_gold_record());
					ps.setInt(2, ui.getGold());
					ps.setByte(3, ui.getFirst());
					ps.setInt(4, ui.getPlayer_id());
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
