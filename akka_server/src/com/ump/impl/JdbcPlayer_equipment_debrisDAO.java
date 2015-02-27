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
import com.ump.model.Player_equipment_debris;

public class JdbcPlayer_equipment_debrisDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayer_equipment_debrisDAO instance;

	public static JdbcPlayer_equipment_debrisDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_equipment_debrisDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_equipment_debris getPlayer_equipment_debris(int playerId) {

		try {
			String sql = "select * from player_equipment_debris where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_equipment_debris>(Player_equipment_debris.class));
			if (o != null) {
				return (Player_equipment_debris) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("player_equipment_debris not found " + playerId, logger);
		}
		return null;

	}

	public JdbcPlayer_equipment_debrisDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean addList(final Player_equipment_debris[] list) {
		try {
			String sql = "INSERT INTO player_equipment_debris " + "(player_id,debris_max_num,debris_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_equipment_debris ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getDebris_max_num());
					ps.setString(3, ui.getDebris_json());
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

	public boolean upDateList(final Player_equipment_debris[] list) {
		try {
			String sql = "UPDATE player_equipment_debris SET debris_max_num=?,debris_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_equipment_debris ui = list[i];
					ps.setInt(1, ui.getDebris_max_num());
					ps.setString(2, ui.getDebris_json());
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
