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
import com.ump.model.Player_equipments;

public class JdbcPlayer_equipmentsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayer_equipmentsDAO instance;

	public static JdbcPlayer_equipmentsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_equipmentsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_equipments getPlayer_equipments(int playerId) {

		try {
			String sql = "select * from player_equipments where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_equipments>(Player_equipments.class));
			if (o != null) {
				return (Player_equipments) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("player_equipments not found " + playerId, logger);
		}
		return null;

	}

	public JdbcPlayer_equipmentsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean addList(final Player_equipments[] list) {
		try {
			String sql = "INSERT INTO player_equipments " + "(player_id,equipment_max_num,equimpment_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_equipments ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getEquipment_max_num());
					ps.setString(3, ui.getEquipment_json());
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

	public boolean upDateList(final Player_equipments[] list) {
		try {
			String sql = "UPDATE player_equipments SET equipment_max_num=?,equimpment_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_equipments ui = list[i];
					ps.setInt(1, ui.getEquipment_max_num());
					ps.setString(2, ui.getEquipment_json());
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
