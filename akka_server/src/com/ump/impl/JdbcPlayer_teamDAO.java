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
import com.ump.model.Player_team;

public class JdbcPlayer_teamDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_teamDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	public static JdbcPlayer_teamDAO instance;

	public static JdbcPlayer_teamDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_teamDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_team getPlayer_team(int playerId) {

		try {
			String sql = "select * from player_team where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Player_team) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_team not found " + playerId, logger);
		}
		return null;

	}
	public boolean addList(final Player_team[] list) {
		try {
			String sql = "INSERT INTO player_team " + "(team1_id,team2_id,team3_id,team4_id,team5_id,team6_id,team7_id,team8_id,player_id) VALUES " + "(?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_team ui = list[i];
					ps.setInt(1, ui.getTeam1_id());
					ps.setInt(2, ui.getTeam2_id());
					ps.setInt(3, ui.getTeam3_id());
					ps.setInt(4, ui.getTeam4_id());
					ps.setInt(5, ui.getTeam5_id());
					ps.setInt(6, ui.getTeam6_id());
					ps.setInt(7, ui.getTeam7_id());
					ps.setInt(8, ui.getTeam8_id());
					ps.setInt(9, ui.getPlayer_id());
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

	public boolean upDateList(final Player_team[] list) {
		try {
			String sql = "UPDATE player_team SET star_num=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_team ui = list[i];
					ps.setInt(1, ui.getTeam1_id());
					ps.setInt(2, ui.getTeam2_id());
					ps.setInt(3, ui.getTeam3_id());
					ps.setInt(4, ui.getTeam4_id());
					ps.setInt(5, ui.getTeam5_id());
					ps.setInt(6, ui.getTeam6_id());
					ps.setInt(7, ui.getTeam7_id());
					ps.setInt(8, ui.getTeam8_id());
					ps.setInt(9, ui.getPlayer_id());
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
