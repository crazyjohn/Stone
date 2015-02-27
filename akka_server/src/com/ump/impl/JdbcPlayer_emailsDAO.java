package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.ump.model.Player_emails;

public class JdbcPlayer_emailsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayer_emailsDAO instance;

	public static JdbcPlayer_emailsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_emailsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcPlayer_emailsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<Player_emails> getList(int playerId) {
		String sql = "select * from player_emails where player_id = " + playerId;
		ArrayList<Player_emails> temp = (ArrayList<Player_emails>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Player_emails.class));
		return temp;
	}

	public boolean addList(final Player_emails[] list) {
		try {
			String sql = "INSERT INTO player_emails " + "(player_id,context,sender_name,item_json,status,type,go) VALUES " + "(?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_emails ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getContext());
					ps.setString(3, ui.getSender_name());
					ps.setString(4, ui.getItem_json());
					ps.setByte(5, ui.getStatus());
					ps.setByte(6, ui.getType());
					ps.setByte(7, ui.getGo());
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

	public boolean upDateList(final Player_emails[] list) {
		try {
			String sql = "UPDATE player_emails SET context=?,sender_name=?, item_json=?,status=?,type=?,go=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_emails ui = list[i];
					ps.setString(1, ui.getContext());
					ps.setString(2, ui.getSender_name());
					ps.setString(3, ui.getItem_json());
					ps.setByte(4, ui.getStatus());
					ps.setByte(5, ui.getType());
					ps.setByte(6, ui.getGo());
					ps.setInt(7, ui.getPlayer_id());
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
