package com.ump.model;

public class Player_friends extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_friends";
//	}
	public static final String tableName = "#player_friends";
	private int player_id;
	private String friend_json;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getFriend_json() {
		return friend_json;
	}
	public void setFriend_json(String friend_json) {
		this.friend_json = friend_json;
	}
	
}
