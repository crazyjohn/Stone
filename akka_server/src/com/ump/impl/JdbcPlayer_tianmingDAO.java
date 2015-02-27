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
import com.ump.model.Player_tianming;

public class JdbcPlayer_tianmingDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_tianmingDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public static JdbcPlayer_tianmingDAO instance;

	public static JdbcPlayer_tianmingDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_tianmingDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_tianming getPlayer_tianming(int playerId) {

		try {
			String sql = "select * from player_tianming where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_tianming>(Player_tianming.class));
			if (o != null) {
				return (Player_tianming) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_tianming not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_tianming[] list) {
		try {
			String sql = "INSERT INTO player_tianming " + "(player_id,level) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_tianming ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getLevel());
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

	public boolean upDateList(final Player_tianming[] list) {
		try {
			String sql = "UPDATE player_tianming SET level=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_tianming ui = list[i];
					ps.setInt(1, ui.getLevel());
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
