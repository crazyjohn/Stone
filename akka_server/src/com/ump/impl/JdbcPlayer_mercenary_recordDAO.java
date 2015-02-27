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
import com.ump.model.Player_mercenary_record;

public class JdbcPlayer_mercenary_recordDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_mercenary_recordDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_mercenary_recordDAO instance;

	public static JdbcPlayer_mercenary_recordDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_mercenary_recordDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_mercenary_record getPlayerMercenaryRecord(int playerId) {
		try {
			String sql = "select * from player_mercenary_record where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_mercenary_record>(Player_mercenary_record.class));
			if (o != null) {
				return (Player_mercenary_record) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_mercenary_record not found " + playerId, logger);
		}
		return null;
	}

	public boolean addList(final Player_mercenary_record[] list) {
		try {
			String sql = "INSERT INTO player_mercenary_record " + "(player_id,json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_record ui = list[i];
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

	public boolean upDateList(final Player_mercenary_record[] list) {
		try {
			String sql = "UPDATE player_mercenary_record SET json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_record ui = list[i];
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
