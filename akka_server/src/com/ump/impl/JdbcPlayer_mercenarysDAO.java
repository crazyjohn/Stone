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
import com.ump.model.Player_mercenarys;

public class JdbcPlayer_mercenarysDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_mercenarysDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public static JdbcPlayer_mercenarysDAO instance;

	public static JdbcPlayer_mercenarysDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_mercenarysDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_mercenarys getPlayer_mercenarys(int playerId) {

		try {
			String sql = "select * from player_mercenarys where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_mercenarys>(Player_mercenarys.class));
			if (o != null) {
				return (Player_mercenarys) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_mercenarys not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_mercenarys[] list) {
		try {
			String sql = "INSERT INTO player_mercenarys " + "(player_id,mercenary_max_num,mercenary_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenarys ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getMercenary_max_num());
					ps.setString(3, ui.getMercenary_json());
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

	public boolean upDateList(final Player_mercenarys[] list) {
		try {
			String sql = "UPDATE player_mercenarys SET mercenary_max_num=?,mercenary_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenarys ui = list[i];
					ps.setInt(1, ui.getMercenary_max_num());
					ps.setString(2, ui.getMercenary_json());
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
