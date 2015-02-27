package com.ump.model;

public class Mercenary_extra extends DBCache{
	
	public static final String tableName = "#mercenary_extra";
	private int extra_id;
	private byte level;
	private int exp;
	private int hp;
	private int atk;
	private int w_def;
	private int n_def;
	public int getExtra_id() {
		return extra_id;
	}
	public void setExtra_id(int extra_id) {
		this.extra_id = extra_id;
	}
	public byte getLevel() {
		return level;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getW_def() {
		return w_def;
	}
	public void setW_def(int w_def) {
		this.w_def = w_def;
	}
	public int getN_def() {
		return n_def;
	}
	public void setN_def(int n_def) {
		this.n_def = n_def;
	}
	
}
