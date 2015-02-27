package com.ump.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.i4joy.util.Tools;
import com.ump.model.Mercenary;
import com.ump.model.Team;

public class JdbcMercenaryDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcMercenaryDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcMercenaryDAO instance;

	public static JdbcMercenaryDAO getInstance() {
		if (instance == null) {
			instance = new JdbcMercenaryDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public List<Mercenary> getMercenaryList(String[] strs) {
		try {
			String sql = "select * from mercenary where mercenary_id in (";

			for (int i = 0; i < strs.length; i++) {
				sql += "'" + strs[i] + "'";
				if (i < strs.length - 1) {
					sql += ",";
				}
			}
			sql += ")";
			return (ArrayList<Mercenary>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Mercenary.class));
		} catch (Exception e) {
			Tools.printlnInfo("Team not found " + strs.toString(), logger);
		}
		return null;
	}

	public Mercenary getMercenary(int mercenaryId) {

		try {
			String sql = "select * from mercenary where mercenary_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { mercenaryId }, new BeanPropertyRowMapper<Mercenary>(Mercenary.class));
			if (o != null) {
				return (Mercenary) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Mercenary not found " + mercenaryId, logger);
		}
		return null;
	}

	public long getMaxId() {
		try {
			final Mercenary mercenary = new Mercenary();
			mercenary.setMercenary_id(1);
			String sql = "select max(mercenary_id) from mercenary";
			getJdbcTemplate().query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					mercenary.setMercenary_id(rs.getLong(1));
				}
			});
			return mercenary.getMercenary_id();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public boolean addList(final Mercenary[] list) {
		try {
			String sql = "INSERT INTO mercenary " + "(mercenary_id,base_id,quality,level,exp,friendly_level,friendly_exp,owner_id,created_time,meridians_level) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Mercenary m = list[i];
					ps.setLong(1, m.getMercenary_id());
					ps.setInt(2, m.getBase_id());
					ps.setByte(3, m.getQuality());
					ps.setByte(4, m.getLevel());
					ps.setInt(5, m.getExp());
					ps.setInt(6, m.getFriendly_level());
					ps.setInt(7, m.getFriendly_exp());
					ps.setLong(8, m.getOwner_id());
					ps.setDate(9, new Date(m.getCreated_time().getTime()));
					ps.setInt(10, m.getMeridians_level());
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

	public boolean upDateList(final Mercenary[] list) {
		try {
			String sql = "UPDATE mercenary SET quality=?,level=?,exp=?,friendly_level=?,friendly_exp=?,meridians_level=?" + " WHERE mercenary_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Mercenary m = list[i];
					ps.setByte(1, m.getQuality());
					ps.setByte(2, m.getLevel());
					ps.setInt(3, m.getExp());
					ps.setInt(4, m.getFriendly_level());
					ps.setInt(5, m.getFriendly_exp());
					ps.setInt(6, m.getMeridians_level());
					ps.setLong(7, m.getMercenary_id());
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
