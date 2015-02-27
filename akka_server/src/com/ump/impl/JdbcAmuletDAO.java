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
import com.ump.model.Amulet;
import com.ump.model.Player_gold;

public class JdbcAmuletDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcAmuletDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcAmuletDAO instance;

	public static JdbcAmuletDAO getInstance() {
		if (instance == null) {
			instance = new JdbcAmuletDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public long getMaxId() {
		try {
			final Amulet amulet = new Amulet();
			amulet.setAmulet_id(1);
			String sql = "select max(amulet_id) from amulet";
			getJdbcTemplate().query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					amulet.setAmulet_id(rs.getLong(1));
				}
			});
			return amulet.getAmulet_id();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public List<Amulet> getAmuletList(String[] strs) {
		try {
			String sql = "select * from amulet where amulet_id in (";

			for (int i = 0; i < strs.length; i++) {
				sql += "'" + strs[i] + "'";
				if (i < strs.length - 1) {
					sql += ",";
				}
			}
			sql += ")";
			return (ArrayList<Amulet>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Amulet.class));
		} catch (Exception e) {
			Tools.printlnInfo("Amulet not found " + strs.toString(), logger);
		}
		return null;
	}

	public Amulet getAmulet(int amuletId) {

		try {
			String sql = "select * from amulet where amulet_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { amuletId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Amulet) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("amulet not found " + amuletId, logger);
		}
		return null;

	}

	public boolean addList(final Amulet[] list) {
		try {
			String sql = "INSERT INTO amulet " + "(base_id,level,amulet_id,owner_id,created_time,refine_level,exp) VALUES " + "(?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Amulet ui = list[i];
					ps.setInt(1, ui.getBase_id());
					ps.setInt(2, ui.getLevel());
					ps.setLong(3, ui.getAmulet_id());
					ps.setLong(4, ui.getOwner_id());
					ps.setDate(5, new Date(ui.getCreated_time().getTime()));
					ps.setInt(6, ui.getRefine_level());
					ps.setInt(7, ui.getExp());
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

	public boolean upDateList(final Amulet[] list) {
		try {
			String sql = "UPDATE amulet SET base_id=?,level=?,refine_level=?,owner_id=?,exp=?" + " WHERE amulet_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Amulet ui = list[i];
					ps.setInt(1, ui.getBase_id());
					ps.setInt(2, ui.getLevel());
					ps.setInt(3, ui.getRefine_level());
					ps.setLong(4, ui.getOwner_id());
					ps.setInt(5, ui.getExp());
					ps.setLong(6, ui.getAmulet_id());
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
