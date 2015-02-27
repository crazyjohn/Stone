package com.ump.model;

public class Player_gold extends DBCache{
	
	public static final String tableName = "#player_gold";
	private int id;
	private int player_id;
	private int player_gold_record;
	private int gold;
	private byte first;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getPlayer_gold_record() {
		return player_gold_record;
	}

	public void setPlayer_gold_record(int player_gold_record) {
		this.player_gold_record = player_gold_record;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public byte getFirst() {
		return first;
	}

	public void setFirst(byte first) {
		this.first = first;
	}

}
