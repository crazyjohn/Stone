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
import com.ump.model.Equipment;
import com.ump.model.Mercenary;

public class JdbcEquipmentDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcEquipmentDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcEquipmentDAO instance;

	public static JdbcEquipmentDAO getInstance() {
		if (instance == null) {
			instance = new JdbcEquipmentDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}
	
	public long getMaxId() {
		try {
			final Equipment equipment = new Equipment();
			equipment.setEquipment_id(1);
			String sql = "select max(equipment_id) from equipment";
			getJdbcTemplate().query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					equipment.setEquipment_id(rs.getLong(1));
				}
			});
			return equipment.getEquipment_id();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public List<Equipment> getEquipmentList(String[] strs) {
		try {
			String sql = "select * from equipment where equipment_id in (";

			for (int i = 0; i < strs.length; i++) {
				sql += "'" + strs[i] + "'";
				if (i < strs.length - 1) {
					sql += ",";
				}
			}
			sql += ")";
			return (ArrayList<Equipment>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Equipment.class));
		} catch (Exception e) {
			Tools.printlnInfo("Equipment not found " + strs.toString(), logger);
		}
		return null;
	}

	public Equipment getEquipment(int Equipment_id) {

		try {
			String sql = "select * from equipment where equipment_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { Equipment_id }, new BeanPropertyRowMapper<Equipment>(Equipment.class));
			if (o != null) {
				return (Equipment) o;
			}

		} catch (Exception e) {
			Tools.printlnInfo("Equipment not found " + Equipment_id, logger);
		}
		return null;

	}

	public boolean addList(final Equipment[] list) {
		try {
			String sql = "INSERT INTO equipment " + "(base_id,level,use_stone_num,equipment_json,equipment_id,created_time,owner_id) VALUES " + "(?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Equipment ui = list[i];
					ps.setInt(1, ui.getBase_id());
					ps.setInt(2, ui.getLevel());
					ps.setInt(3, ui.getUse_stone_num());
					ps.setString(4, ui.getEquipment_json());
					ps.setLong(5, ui.getEquipment_id());
					ps.setDate(6, new Date(ui.getCreated_time().getTime()));
					ps.setLong(7, ui.getOwner_id());
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

	public boolean upDateList(final Equipment[] list) {
		try {
			String sql = "UPDATE equipment SET base_id=?, level=?, use_stone_num=?,equipment_json=?" + " WHERE equipment_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Equipment ui = list[i];
					ps.setInt(1, ui.getBase_id());
					ps.setInt(2, ui.getLevel());
					ps.setInt(3, ui.getUse_stone_num());
					ps.setString(4, ui.getEquipment_json());
					ps.setLong(5, ui.getEquipment_id());
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
