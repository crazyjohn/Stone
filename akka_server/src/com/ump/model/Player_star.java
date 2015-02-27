package com.ump.model;

public class Player_star extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_star";
//	}
	public static final String tableName = "#player_star";
	private int player_id;
	private int star_num;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getStar_num() {
		return star_num;
	}
	public void setStar_num(int star_num) {
		this.star_num = star_num;
	}
	
}
