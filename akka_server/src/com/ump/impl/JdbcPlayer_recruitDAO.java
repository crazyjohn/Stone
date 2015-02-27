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
import com.ump.model.Player_recruit;

public class JdbcPlayer_recruitDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_recruitDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_recruitDAO instance;

	public static JdbcPlayer_recruitDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_recruitDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_recruit getPlayer_recruit(int playerId) {

		try {
			String sql = "select * from player_recruit where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_recruit>(Player_recruit.class));
			if (o != null) {
				return (Player_recruit) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_recruit not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_recruit[] list) {
		try {
			String sql = "INSERT INTO player_recruit " + "(player_id, recruit_num,remain_time_3_5,remain_time_4_5,lucky) VALUES " + "(?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_recruit ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getRecruit_num());
					ps.setLong(3, ui.getRemain_time_3_5());
					ps.setLong(4, ui.getRemain_time_4_5());
					ps.setInt(5, ui.getLucky());
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

	public boolean upDateList(final Player_recruit[] list) {
		try {
			String sql = "UPDATE player_recruit SET recruit_num=?,remain_time_3_5=?,remain_time_4_5=?,lucky=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_recruit ui = list[i];
					ps.setInt(1, ui.getRecruit_num());
					ps.setLong(2, ui.getRemain_time_3_5());
					ps.setLong(3, ui.getRemain_time_4_5());
					ps.setInt(4, ui.getLucky());
					ps.setInt(5, ui.getPlayer_id());
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
