package com.ump.impl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.i4joy.util.Tools;
import com.ump.model.User_players;

public class JdbcUser_playersDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	public JdbcUser_playersDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public void add(User_players up) {
		try {
			String sql = "INSERT INTO user_players (username,password,players_json) VALUES " + "('" + up.getUsername() + "','" + up.getPassword() + "','" + up.getPlayers_json() + "')";
			getJdbcTemplate().update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User_players get(String userName) {
		try {
			String sql = "Select * from user_players where username = ?";
			return (User_players) getJdbcTemplate().queryForObject(sql, new Object[] { userName }, new BeanPropertyRowMapper(User_players.class));
		} catch (Exception e) {
			// e.printStackTrace();
			Tools.printlnInfo("没有找到名字 " + userName, logger);
		}
		return null;
	}

	public HashMap<String, JSONObject> getAll() {
		HashMap<String, JSONObject> hm = new HashMap<String, JSONObject>();
		try {
			int offset = 5000;
			int index = 0;
			while (true) {
				String sql = "select * from player_info where id >= " + (index * offset) + " and id <= " + ((index + 1) * offset);
				ArrayList<User_players> temp = (ArrayList<User_players>) getJdbcTemplate().query(sql, new BeanPropertyRowMapper(User_players.class));
				index++;
				if (temp == null || temp.size() == 0) {
					break;
				}
				for (int i = 0; i < temp.size(); i++) {
					hm.put(temp.get(i).getUsername() + temp.get(i).getUsername(), JSONObject.fromObject(temp.get(i).getPlayers_json()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

}
