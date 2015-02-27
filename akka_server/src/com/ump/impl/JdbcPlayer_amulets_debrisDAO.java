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
import com.ump.model.Player_amulets_debris;

public class JdbcPlayer_amulets_debrisDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_amulets_debrisDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_amulets_debrisDAO instance;

	public static JdbcPlayer_amulets_debrisDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_amulets_debrisDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_amulets_debris getEquipment_debris(int equipment_debris_id) {

		try {
			String sql = "select * from player_amulets_debris where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { equipment_debris_id }, new BeanPropertyRowMapper<Player_amulets_debris>(Player_amulets_debris.class));
			if (o != null) {
				return (Player_amulets_debris) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("equipment_debris not found " + equipment_debris_id, logger);
		}
		return null;

	}

	public boolean addList(final Player_amulets_debris[] list) {
		try {
			String sql = "INSERT INTO player_amulets_debris " + "(player_id,amulet_json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_amulets_debris ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getAmulet_debris_json());
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

	public boolean upDateList(final Player_amulets_debris[] list) {
		try {
			String sql = "UPDATE player_amulets_debris SET amulet_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_amulets_debris ui = list[i];
					ps.setString(1, ui.getAmulet_debris_json());
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
