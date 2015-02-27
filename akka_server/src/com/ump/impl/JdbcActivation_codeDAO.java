package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.util.Tools;
import com.ump.model.Activation_code;

public class JdbcActivation_codeDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcActivation_codeDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean add(Activation_code activationCode) {
		try {
			String sql = "INSERT INTO activation_code (user_id,num) VALUES (?,?)";
			getJdbcTemplate().update(sql, new Object[] { activationCode.getUser_id(), activationCode.getNum() });
			return true;
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return false;
	}

	public Activation_code get(String userId) {
		String sql = "select * from Activation_code where user_id = " + userId;
		try {
			return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<Activation_code>(Activation_code.class));
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return null;
	}

	public boolean upDateList(LinkedList<Activation_code> list) {
		try {
			HashMap<Integer, Activation_code> hm = new HashMap<Integer, Activation_code>();

			Activation_code rap;
			for (int i = 0; i < list.size(); i++) {
				rap = list.get(i);
				hm.put(rap.getUser_id(), rap);
			}
			Activation_code[] raps = new Activation_code[hm.size()];
			hm.values().toArray(raps);
			LinkedList<Activation_code> tempList = new LinkedList<Activation_code>();
			for (int i = 0; i < raps.length; i++) {
				tempList.add(raps[i]);
			}

			final LinkedList<Activation_code> _list = tempList;
			String sql = "UPDATE activation_code SET num = ? WHERE  user_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Activation_code ue = _list.get(i);
					ps.setLong(1, ue.getNum());
				}

				public int getBatchSize() {
					return _list.size();
				}

			});
			return true;
		} catch (Exception e) {
			Tools.printError(e, logger, null);
		}
		return false;
	}

}
