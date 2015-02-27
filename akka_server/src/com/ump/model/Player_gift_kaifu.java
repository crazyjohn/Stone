package com.ump.model;

public class Player_gift_kaifu extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_gift_kaifu";
//	}
	public static final String tableName = "#player_gift_kaifu";
	private int player_id;
	private String json;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}	
}
