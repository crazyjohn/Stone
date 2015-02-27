package com.ump.model;

public class Player_elite_battle extends DBCache{
	public static final String tableName = "#player_elite_battle";
	private int player_id;
	private int elite_battle_num;
	private long time;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getElite_battle_num() {
		return elite_battle_num;
	}
	public void setElite_battle_num(int elite_battle_num) {
		this.elite_battle_num = elite_battle_num;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
