package com.ump.model;

public class Amulet_extra extends DBCache{
	
	public static final String tableName = "#amulet_extra";
	private int extra_id;
	private int hp;
	public int getExtra_id() {
		return extra_id;
	}
	public void setExtra_id(int extra_id) {
		this.extra_id = extra_id;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	
}
