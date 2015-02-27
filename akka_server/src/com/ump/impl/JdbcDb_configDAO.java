package com.ump.impl;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ump.model.Db_config;

public class JdbcDb_configDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcDb_configDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ArrayList<Db_config> getALL() {
		String sql = "select * from db_config";
		ArrayList<Db_config> temp = (ArrayList<Db_config>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Db_config.class));
		return temp;
	}
}
