package com.ump.model;

public class Player_friends_give extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_friends_give";
//	}
	public static final String tableName = "#player_friends_give";
	private int player_id;
	private int friend_give_num;
	private long time;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getFriend_give_num() {
		return friend_give_num;
	}
	public void setFriend_give_num(int friend_give_num) {
		this.friend_give_num = friend_give_num;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
