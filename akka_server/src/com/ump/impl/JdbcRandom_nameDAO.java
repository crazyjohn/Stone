package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ump.model.Random_name;

public class JdbcRandom_nameDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcRandom_nameDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public HashMap<String, Random_name> getALL() {
		HashMap<String, Random_name> hm = new HashMap<String, Random_name>();
		try {
			int offset = 5000;
			int index = 0;
			while (true) {
				String sql = "select * from random_name where id >= " + (index * offset) + " and id <= " + ((index + 1) * offset);
				ArrayList<Random_name> temp = (ArrayList<Random_name>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Random_name.class));
				index++;
				if (temp == null || temp.size() == 0) {
					break;
				}
				for (int i = 0; i < temp.size(); i++) {
					hm.put(temp.get(i).getName(), temp.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	public boolean upDateList(final ArrayList<Random_name> uis) {
		try {
			String sql = "UPDATE random_name SET state = ? " + " WHERE name = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Random_name ui = uis.get(i);
					ps.setString(1, ui.getName());
					ps.setInt(2, ui.getState());
				}

				public int getBatchSize() {
					return uis.size();
				}

			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
