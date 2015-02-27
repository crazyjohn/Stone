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
import com.ump.model.Player_star;

public class JdbcPlayer_starDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_starDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	public static JdbcPlayer_starDAO instance;

	public static JdbcPlayer_starDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_starDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_star getPlayer_star(int playerId) {

		try {
			String sql = "select * from player_star where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_star>(Player_star.class));
			if (o != null) {
				return (Player_star) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_star not found " + playerId, logger);
		}
		return null;

	}
	public boolean addList(final Player_star[] list) {
		try {
			String sql = "INSERT INTO player_star " + "(player_id,star_num) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_star ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getStar_num());
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

	public boolean upDateList(final Player_star[] list) {
		try {
			String sql = "UPDATE player_star SET star_num=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_star ui = list[i];
					ps.setInt(1, ui.getStar_num());
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
