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
import com.ump.model.Equipment_extra;

public class JdbcEquipment_extraDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcEquipment_extraDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcEquipment_extraDAO instance;

	public static JdbcEquipment_extraDAO getInstance() {
		if (instance == null) {
			instance = new JdbcEquipment_extraDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public Equipment_extra getEquipment_extra(long extraId) {

		try {
			String sql = "select * from equipment_extra where equipment_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { extraId }, new BeanPropertyRowMapper<Equipment_extra>(Equipment_extra.class));
			if (o != null) {
				return (Equipment_extra) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Equipment_extra not found " + extraId, logger);
		}
		return null;

	}

	public List<Equipment_extra> getEquipmentList(String[] strs) {
		try {
			String sql = "select * from equipment where equipment_id in (";

			for (int i = 0; i < strs.length; i++) {
				sql += "'" + strs[i] + "'";
				if (i < strs.length - 1) {
					sql += ",";
				}
			}
			sql += ")";
			return (ArrayList<Equipment_extra>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Equipment_extra.class));
		} catch (Exception e) {
			Tools.printlnInfo("Equipment not found " + strs.toString(), logger);
		}
		return null;
	}

	public boolean addList(final Equipment_extra[] list) {
		try {
			String sql = "INSERT INTO equipment_extra " + "(hp,atk,w_def,n_def,double_hit,tenacity,hit,stone_num,equipment_id,owner_id) VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Equipment_extra ui = list[i];
					ps.setInt(1, ui.getHp());
					ps.setInt(2, ui.getAtk());
					ps.setInt(3, ui.getW_def());
					ps.setInt(4, ui.getN_def());
					ps.setInt(5, ui.getDouble_hit());
					ps.setInt(6, ui.getTenacity());
					ps.setInt(7, ui.getHit());
					ps.setInt(8, ui.getStone_num());
					ps.setLong(9, ui.getEquipment_id());
					ps.setInt(10, ui.getMiss());
					ps.setInt(11, ui.getParry());
					ps.setInt(12, ui.getPuncture());
					ps.setLong(13, ui.getOwner_id());
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

	public boolean upDateList(final Equipment_extra[] list) {
		try {
			String sql = "UPDATE equipment_extra SET hp=?,atk=?, w_def=?, n_def=?, double_hit=?,tenacity=?,hit=?,miss=?,parry=?,puncture=?" + " WHERE equipment_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Equipment_extra ui = list[i];
					ps.setInt(1, ui.getHp());
					ps.setInt(2, ui.getAtk());
					ps.setInt(3, ui.getW_def());
					ps.setInt(4, ui.getN_def());
					ps.setInt(5, ui.getDouble_hit());
					ps.setInt(6, ui.getTenacity());
					ps.setInt(7, ui.getHit());
					ps.setInt(8, ui.getMiss());
					ps.setInt(9, ui.getParry());
					ps.setInt(10, ui.getPuncture());
					ps.setLong(11, ui.getEquipment_id());
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
