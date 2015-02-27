package com.ump.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.i4joy.util.Tools;
import com.mysql.jdbc.Statement;
import com.ump.model.Player_info;

public class JdbcPlayer_infoDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_infoDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public int add(Player_info mi) {
		try {
			final String sqlTest = "INSERT INTO player_info (player_name,username,server_id,db_id) VALUES " + "('" + mi.getPlayer_name() + "','" + mi.getUsername() + "'," + mi.getServer_id() + "',"
					+ mi.getDb_id()+ ")";
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sqlTest, Statement.RETURN_GENERATED_KEYS);
					return ps;
				}

			}, keyHolder);
			long id = keyHolder.getKey().longValue();
			return (int) id;
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return 0;
	}

	public Player_info get(Player_info pi) {
		String sql = "select player_id,username,server_id,base_id from player_info where player_name = '" + pi.getPlayer_name() + "'";
		final Player_info newPI = new Player_info();
		getJdbcTemplate().query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				newPI.setPlayer_id(rs.getInt(1));
				newPI.setUsername(rs.getString(2));
				newPI.setServer_id(rs.getShort(3));
				newPI.setDb_id(rs.getByte(4));
			}
		});
		newPI.setPlayer_name(pi.getPlayer_name());
		return newPI;
	}

	public HashMap<String, Player_info> getALL() {
		HashMap<String, Player_info> hm = new HashMap<String, Player_info>();
		try {

			int offset = 5000;
			int index = 0;
			while (true) {
				String sql = "select * from player_info where player_id >= " + (index * offset) + " and player_id <= " + ((index + 1) * offset);
				ArrayList<Player_info> temp = (ArrayList<Player_info>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Player_info.class));
				index++;
				if (temp == null || temp.size() == 0) {
					break;
				}
				for (int i = 0; i < temp.size(); i++) {
					hm.put(temp.get(i).getPlayer_name(), temp.get(i));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	public void addList(final Player_info[] list) {

		try {
			String sql = "INSERT INTO player_info " + "(player_name,server_id,username,last_time,player_id,db_id) VALUES " + "(?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_info ui = list[i];
					ps.setString(1, ui.getPlayer_name());
					ps.setShort(2, ui.getServer_id());
					ps.setString(3, ui.getUsername());
					ps.setLong(4, ui.getLast_time());
					ps.setInt(5, ui.getPlayer_id());
					ps.setByte(6, ui.getDb_id());
				}

				public int getBatchSize() {
					return list.length;
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
