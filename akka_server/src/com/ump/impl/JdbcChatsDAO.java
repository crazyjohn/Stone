package com.ump.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.akka.kok.db.DBDataSourceUser;
import com.ump.model.Chats;

public class JdbcChatsDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcChatsDAO instance;

	public static JdbcChatsDAO getInstance() {
		if (instance == null) {
			instance = new JdbcChatsDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcChatsDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public boolean addList(final ArrayList<Chats> list) {
		try {
			String sql = "INSERT INTO chats " + "(receiver_name,sender_name,type,context) VALUES " + "(?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Chats o = list.get(i);
					o.setReceiver_name(o.getReceiver_name());
					o.setSender_name(o.getSender_name());
					o.setType(o.getType());
					o.setContext(o.getContext());
				}

				public int getBatchSize() {
					return list.size();
				}

			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
