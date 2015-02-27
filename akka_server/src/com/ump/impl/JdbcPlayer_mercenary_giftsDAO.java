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
import com.ump.model.Player_mercenary_gifts;
import com.ump.model.Player_gold;

public class JdbcPlayer_mercenary_giftsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_mercenary_giftsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_mercenary_giftsDAO instance;

	public static JdbcPlayer_mercenary_giftsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_mercenary_giftsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_mercenary_gifts getPlayerMercenaryGift(int playerId) {

		try {
			String sql = "select * from player_mercenary_gifts where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Player_mercenary_gifts) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Item not found " + playerId, logger);
		}
		return null;

	}

	public boolean addList(final Player_mercenary_gifts[] list) {
		try {
			String sql = "INSERT INTO player_mercenary_gifts " + "(player_id,mercenary_gifts_json) VALUES " + "(?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_gifts ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getMercenary_gifts_json());
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

	public boolean upDateList(final Player_mercenary_gifts[] list) {
		try {
			String sql = "UPDATE player_mercenary_gifts SET mercenary_gifts_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_mercenary_gifts ui = list[i];
					ps.setString(1, ui.getMercenary_gifts_json());
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
