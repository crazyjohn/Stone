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
import com.ump.model.Player;

public class JdbcPlayerDAO extends JdbcDaoSupport {
	protected final Log logger = LogFactory.getLog(getClass());
	public static JdbcPlayerDAO instance;

	public static JdbcPlayerDAO getInstance() {
		if (instance == null) {
			instance = new JdbcPlayerDAO(DBDataSourceUser.dataSource);
		}
		return instance;
	}

	public JdbcPlayerDAO(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public Player getPlayerById(int playerId) {
		try {
			String sql = "select * from player where player_id = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerId }, new BeanPropertyRowMapper<Player>(Player.class));
			if (o != null) {
				return (Player) o;
			}

		} catch (Exception e) {
			// Tools.printError(e, logger);
			Tools.printlnInfo("player not found " + playerId, logger);
		}
		return null;
	}

	public Player getPlayerByName(String playerName) {
		try {
			String sql = "select * from player where player_name = ?";
			Object o = getJdbcTemplate().queryForObject(sql, new Object[] { playerName }, new BeanPropertyRowMapper<Player>(Player.class));
			if (o != null) {
				return (Player) o;
			}

		} catch (Exception e) {
			// Tools.printError(e, logger);
			Tools.printlnInfo("player not found " + playerName, logger);
		}
		return null;
	}

	public boolean addPlayer(Player player) {
		try {
			String sql = "INSERT INTO player " + "(base_id,player_id,player_name,money,mercenary_exp,yu,stamina_pve,stamina_pvp,reputation,stamina_pvp_limit,rmb_gold,free_gold,star_num,rebirth_num) VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(
					sql,
					new Object[] { player.getPlayer_id(), player.getPlayer_name(), player.getMoney(), player.getMercenary_exp(), player.getYu(), player.getStamina_pve(), player.getStamina_pvp(), player.getReputation(), player.getStamina_pvp_limit(),
							player.getRmb_gold(), player.getFree_gold(),player.getRebirth_num()});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addList(final Player[] list) {
		try {
			String sql = "INSERT ignore INTO player " + "(player_id,player_name,money,mercenary_exp,yu,stamina_pve,stamina_pvp,reputation,stamina_pvp_limit,rmb_gold,free_gold,base_id,meridians_level,rebirth_num) VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player ui = list[i];
					ps.setInt(1, ui.getPlayer_id());
					ps.setString(2, ui.getPlayer_name());
//					ps.setInt(3, ui.getMercenary_id());
					ps.setInt(3, ui.getMoney());
					ps.setInt(4, ui.getMercenary_exp());
					ps.setInt(5, ui.getYu());
					ps.setInt(6, ui.getStamina_pve());
					ps.setInt(7, ui.getStamina_pvp());
					ps.setInt(8, ui.getReputation());
					ps.setInt(9, ui.getStamina_pvp_limit());
					ps.setInt(10, ui.getRmb_gold());
					ps.setInt(11, ui.getFree_gold());
					ps.setInt(12, ui.getBase_id());
					ps.setInt(13,ui.getStar_num());
					ps.setInt(14, ui.getMeridians_level());
					ps.setInt(15, ui.getRebirth_num());
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

	public boolean upDateList(final Player[] list) {
		try {
			String sql = "UPDATE player SET money=?,mercenary_exp=?,yu=?,stamina_pve=?,stamina_pvp=?,out_time=?,stamina_pvp_limit=?,rmb_gold=?,free_gold=?,base_id=?,reputation=?,meridians_level=?,star_num=?,rebirth_num=?" + " WHERE player_id = ?";
			getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Player ui = list[i];
//					ps.setInt(1, ui.getMercenary_id());
					ps.setInt(1, ui.getMoney());
					ps.setInt(2, ui.getMercenary_exp());
					ps.setInt(3, ui.getYu());
					ps.setInt(4, ui.getStamina_pve());
					ps.setInt(5, ui.getStamina_pvp());
					ps.setLong(6, ui.getOut_time());
					ps.setInt(7, ui.getStamina_pvp_limit());
					ps.setInt(8, ui.getRmb_gold());
					ps.setInt(9, ui.getFree_gold());
					ps.setInt(10, ui.getBase_id());
					ps.setInt(11, ui.getReputation());
					ps.setInt(12, ui.getMeridians_level());
					ps.setInt(13, ui.getStar_num());
					ps.setInt(14, ui.getRebirth_num());
					ps.setInt(15, ui.getPlayer_id());
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

	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
	}
}
