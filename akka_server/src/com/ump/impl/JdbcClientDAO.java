package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.ump.model.Client;

public class JdbcClientDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcClientDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public static JdbcClientDAO instance;

	public static JdbcClientDAO getInstance() {
		if (instance == null) {
			instance = new JdbcClientDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public boolean addList(final Client[] list) {
		try {
			String sql = "INSERT ignore INTO client " + "(client_id,version,device,channel_id,sourceVersion,create_date) VALUES " + "(?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Client ui = list[i];
					ps.setString(1, ui.getClient_id());
					ps.setString(2, ui.getVersion());
					ps.setString(3, ui.getDevice());
					ps.setString(4, ui.getChannel_id());
					ps.setString(5, ui.getSourceVersion());
					ps.setTimestamp(6, new Timestamp(ui.getCreate_date().getTime()));
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
