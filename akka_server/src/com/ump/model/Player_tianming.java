package com.ump.model;

public class Player_tianming extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_tianming";
//	}
	public static final String tableName = "#player_tianming";
	private int player_id;
	private int level;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	 
}
