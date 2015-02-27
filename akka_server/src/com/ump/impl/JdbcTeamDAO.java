package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.i4joy.util.Tools;
import com.ump.model.Team;

public class JdbcTeamDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcTeamDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcTeamDAO instance;

	public static JdbcTeamDAO getInstance() {
		if (instance == null) {
			instance = new JdbcTeamDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public List<Team> getTeams(String[] teamIds) {

		try {
			String sql = "select * from team where team_id in (";

			for (int i = 0; i < teamIds.length; i++) {
				sql += "'" + teamIds[i]+"'";
				if(i < teamIds.length-1)
				{
					sql +=",";
				}
			}
			sql += ")";
			return (ArrayList<Team>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Team.class));
		} catch (Exception e) {
			Tools.printlnInfo("Team not found " + teamIds.toString(), logger);
		}
		return null;

	}

	public boolean addList(final Team[] list) {
		try {
			String sql = "INSERT INTO team " + "(menceray_id,player_id,hat_id,weapon_id,necklace_id,body_id,book_id,horse_id,team_id,postion) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Team ui = list[i];
					ps.setInt(1, ui.getMenceray_id());
					ps.setInt(2, ui.getPlayer_id());
					ps.setInt(3, ui.getHat_id());
					ps.setInt(4, ui.getWeapon_id());
					ps.setInt(5, ui.getNecklace_id());
					ps.setInt(6, ui.getBody_id());
					ps.setInt(7, ui.getBook_id());
					ps.setInt(8, ui.getHorse_id());
					ps.setString(9, ui.getPlayer_id()+"_"+ui.getPostion());
					ps.setInt(10, ui.getPostion());
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

	public boolean upDateList(final Team[] list) {
		try {
			String sql = "UPDATE team SET menceray_id=?,player_id=?,hat_id=?,weapon_id=?,necklace_id=?,body_id=?,book_id=?,horse_id=?,postion=?" + " WHERE team_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Team ui = list[i];
					ps.setInt(1, ui.getMenceray_id());
					ps.setInt(2, ui.getPlayer_id());
					ps.setInt(3, ui.getHat_id());
					ps.setInt(4, ui.getWeapon_id());
					ps.setInt(5, ui.getNecklace_id());
					ps.setInt(6, ui.getBody_id());
					ps.setInt(7, ui.getBook_id());
					ps.setInt(8, ui.getHorse_id());
					ps.setInt(9, ui.getPostion());
					ps.setString(10, ui.getPlayer_id()+"_"+ui.getPostion ());
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
