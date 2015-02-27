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
import com.ump.model.Player_avatar;
import com.ump.model.Player_gold;

public class JdbcPlayer_avatarDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcPlayer_avatarDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcPlayer_avatarDAO instance;

	public static JdbcPlayer_avatarDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayer_avatarDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Player_avatar getPlayerAvatar(int playerId) {
		try {
			String sql = "select * from player_avatar where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Player_avatar) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Player_avatar not found " + playerId, logger);
		}
		return null;
	}

	public boolean addList(final Player_avatar[] list) {
		try {
			String sql = "INSERT INTO player_avatar " + "(player_id,sex,body_id,body_tex_id,head_id,head_tex_id,face_id,face_tex_id,cloth_id,cloth_tex_id,weapon_id,weapon_tex_id) VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_avatar ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setByte(2, ui.getSex());
					ps.setShort(3, ui.getBody_id());
					ps.setShort(4, ui.getBody_tex_id());
					ps.setShort(5, ui.getHead_id());
					ps.setShort(6, ui.getHead_tex_id());
					ps.setShort(7, ui.getFace_id());
					ps.setShort(8, ui.getFace_tex_id());
					ps.setShort(9, ui.getCloth_id());
					ps.setShort(10, ui.getCloth_tex_id());
					ps.setShort(11, ui.getWeapon_id());
					ps.setShort(12, ui.getWeapon_tex_id());
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

	public boolean upDateList(final Player_avatar[] list) {
		try {
			String sql = "UPDATE player_avatar SET sex=?, body_id=?, body_tex_id=?, head_id=?, head_tex_id=?, face_id=?, face_tex_id=?, cloth_id=?, cloth_tex_id=?, weapon_id=?, weapon_tex_id=?"
					+ " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player_avatar ui = list[i];
					ps.setByte(1, ui.getSex());
					ps.setShort(2, ui.getBody_id());
					ps.setShort(3, ui.getBody_tex_id());
					ps.setShort(4, ui.getHead_id());
					ps.setShort(5, ui.getHead_tex_id());
					ps.setShort(6, ui.getFace_id());
					ps.setShort(7, ui.getFace_tex_id());
					ps.setShort(8, ui.getCloth_id());
					ps.setShort(9, ui.getCloth_tex_id());
					ps.setShort(10, ui.getWeapon_id());
					ps.setShort(11, ui.getWeapon_tex_id());
					ps.setInt(12, ui.getPlayer_id());
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
