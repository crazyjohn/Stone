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
import com.ump.model.Mercenary_extra;
import com.ump.model.Player_gold;

public class JdbcMercenary_extraDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcMercenary_extraDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcMercenary_extraDAO instance;

	public static JdbcMercenary_extraDAO getInstance() {
		if (instance == null) {
			instance = new JdbcMercenary_extraDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Mercenary_extra getMercenary_extra(int mercenary_extraId) {

		try {
			String sql = "select * from mercenary_extra where extra_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { mercenary_extraId }, new BeanPropertyRowMapper<Player_gold>(Player_gold.class));
			if (o != null) {
				return (Mercenary_extra) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Mercenary_extra not found " + mercenary_extraId, logger);
		}
		return null;

	}

	public boolean addList(final Mercenary_extra[] list) {
		try {
			String sql = "INSERT INTO menceray_extra " + "(level,exp,hp,atk,w_def,n_def) VALUES " + "(?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Mercenary_extra ui = list[i];
					ps.setByte(1, ui.getLevel());
					ps.setInt(2, ui.getExp());
					ps.setInt(3, ui.getHp());
					ps.setInt(4, ui.getAtk());
					ps.setInt(5, ui.getW_def());
					ps.setInt(6, ui.getN_def());
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

	public boolean upDateList(final Mercenary_extra[] list) {
		try {
			String sql = "UPDATE item SET level=?, exp=?,hp=?,atk=?,w_def=?,n_def=?" + " WHERE extra_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Mercenary_extra ui = list[i];
					ps.setByte(1, ui.getLevel());
					ps.setInt(2, ui.getExp());
					ps.setInt(3, ui.getHp());
					ps.setInt(4, ui.getAtk());
					ps.setInt(5, ui.getW_def());
					ps.setInt(6, ui.getN_def());
					ps.setInt(7, ui.getExtra_id());
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
