package com.ump.model;

public class Player_activity_battle extends DBCache{
	
	public static final String tableName = "#player_activity_battle";
	private int player_id;
	private int num;
	private long time;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
