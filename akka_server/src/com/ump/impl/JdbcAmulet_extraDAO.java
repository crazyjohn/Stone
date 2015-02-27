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
import com.ump.model.Amulet_extra;
import com.ump.model.Player_gold;

public class JdbcAmulet_extraDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcAmulet_extraDAO instance;

	public static JdbcAmulet_extraDAO getInstance() {
		if (instance == null) {
			instance = new JdbcAmulet_extraDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcAmulet_extraDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public Amulet_extra getAmulet_extra(int amuletId) {

		try {
			String sql = "select * from amulet_extra where extra_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { amuletId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Amulet_extra) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("amulet not found " + amuletId, logger);
		}
		return null;

	}

	public boolean addList(final Amulet_extra[] list) {
		try {
			String sql = "INSERT INTO amulet_extra " + "(hp) VALUES " + "(?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Amulet_extra ui = list[i];
					ps.setInt(1, ui.getHp());
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

	public boolean upDateList(final Amulet_extra[] list) {
		try {
			String sql = "UPDATE amulet_extra SET hp = ?" + " WHERE extra_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Amulet_extra ui = list[i];
					ps.setInt(1, ui.getHp());
					ps.setInt(2, ui.getExtra_id());
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
