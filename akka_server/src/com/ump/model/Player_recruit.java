package com.ump.model;

public class Player_recruit extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_recruit";
//	}
	public static final String tableName = "#player_recruit";
	private int player_id;
	private int recruit_num;
	private long remain_time_3_5;
	private long remain_time_4_5;
	private int lucky;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getRecruit_num() {
		return recruit_num;
	}
	public void setRecruit_num(int recruit_num) {
		this.recruit_num = recruit_num;
	}
	public long getRemain_time_3_5() {
		return remain_time_3_5;
	}
	public void setRemain_time_3_5(long remain_time_3_5) {
		this.remain_time_3_5 = remain_time_3_5;
	}
	public long getRemain_time_4_5() {
		return remain_time_4_5;
	}
	public void setRemain_time_4_5(long remain_time_4_5) {
		this.remain_time_4_5 = remain_time_4_5;
	}
	public int getLucky() {
		return lucky;
	}
	public void setLucky(int lucky) {
		this.lucky = lucky;
	}
	
	
}
