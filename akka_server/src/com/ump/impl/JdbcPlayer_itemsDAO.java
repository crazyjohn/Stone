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
import com.ump.model.Player_items;

public class JdbcPlayer_itemsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_itemsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}
	public static JdbcPlayer_itemsDAO instance;

	public static JdbcPlayer_itemsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_itemsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_items getPlayerItems(int player_id) {

		try {
			String sql = "select * from Player_items where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { player_id }, new BeanPropertyRowMapper<Player_items>(Player_items.class));
			if (o != null) {
				return (Player_items) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_items not found " + player_id, logger);
		}
		return null;

	}
	public boolean addList(final Player_items[] list) {
		try {
			String sql = "INSERT INTO player_items " + "(player_id,item_max_num,item_json) VALUES " + "(?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_items ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setInt(2, ui.getItem_max_num());
					ps.setString(3, ui.getItem_json());
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

	public boolean upDateList(final Player_items[] list) {
		try {
			String sql = "UPDATE player_items SET item_max_num=?,item_json=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_items ui = list[i];
					ps.setInt(1, ui.getItem_max_num());
					ps.setString(2, ui.getItem_json());
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
