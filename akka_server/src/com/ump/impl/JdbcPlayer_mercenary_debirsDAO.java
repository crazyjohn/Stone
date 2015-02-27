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
import com.ump.model.Player_mercenary_debirs;

public class JdbcPlayer_mercenary_debirsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_mercenary_debirsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_mercenary_debirsDAO instance;

	public static JdbcPlayer_mercenary_debirsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_mercenary_debirsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_mercenary_debirs getPlayerMercenaryDebirs(int playerId) {

		try {
			String sql = "select * from player_mercenary_debirs where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_mercenary_debirs>(Player_mercenary_debirs.class));
			if (o != null) {
				return (Player_mercenary_debirs) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_ghosts not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_mercenary_debirs[] list) {
		try {
			String sql = "INSERT INTO player_mercenary_debirs " + "(player_id,debirs_max_num,debirs_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_debirs ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getDebirs_max_num());
					ps.setString(3, ui.getDebirs_json());
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

	public boolean upDateList(final Player_mercenary_debirs[] list) {
		try {
			String sql = "UPDATE player_mercenary_debirs SET debirs_max_num=?,debirs_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_debirs ui = list[i];
					ps.setInt(1, ui.getDebirs_max_num());
					ps.setString(2, ui.getDebirs_json());
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
