package com.ump.model;

/**
 * @author DongLei
 * 
 */
public class Player_team extends DBCache {
	// @Override
	// public String getTableName() {
	// return "#player_team";
	// }
	public static final String tableName = "#player_team";
	private int player_id;
	private int team1_id;
	private int team2_id;
	private int team3_id;
	private int team4_id;
	private int team5_id;
	private int team6_id;
	private int team7_id;
	private int team8_id;

	public int getTeam6_id() {
		return team6_id;
	}

	public void setTeam6_id(int team6_id) {
		this.team6_id = team6_id;
	}

	public int getTeam7_id() {
		return team7_id;
	}

	public void setTeam7_id(int team7_id) {
		this.team7_id = team7_id;
	}

	public int getTeam8_id() {
		return team8_id;
	}

	public void setTeam8_id(int team8_id) {
		this.team8_id = team8_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getTeam1_id() {
		return team1_id;
	}

	public void setTeam1_id(int team1_id) {
		this.team1_id = team1_id;
	}

	public int getTeam2_id() {
		return team2_id;
	}

	public void setTeam2_id(int team2_id) {
		this.team2_id = team2_id;
	}

	public int getTeam3_id() {
		return team3_id;
	}

	public void setTeam3_id(int team3_id) {
		this.team3_id = team3_id;
	}

	public int getTeam4_id() {
		return team4_id;
	}

	public void setTeam4_id(int team4_id) {
		this.team4_id = team4_id;
	}

	public int getTeam5_id() {
		return team5_id;
	}

	public void setTeam5_id(int team5_id) {
		this.team5_id = team5_id;
	}

	public Player_team() {

	}

	public Player_team(Player_team team, byte action) {
		setAction(action);
		this.player_id = team.getPlayer_id();
		this.team1_id = team.getTeam1_id();
		this.team2_id = team.getTeam2_id();
		this.team3_id = team.getTeam3_id();
		this.team4_id = team.getTeam4_id();
		this.team5_id = team.getTeam5_id();
		this.team6_id = team.getTeam6_id();
		this.team7_id = team.getTeam7_id();
		this.team8_id = team.getTeam8_id();
	}

}
