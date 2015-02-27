package com.ump.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

public class Equipment_extra extends DBCache {

	public static final String tableName = "#equipment_extra";
	private long equipment_id;
	private int stone_num;
	private int hp;
	private int atk;
	private int w_def;
	private int n_def;
	private int double_hit;
	private int tenacity;
	private int hit;
	private int miss;
	private int parry;
	private int puncture;
	private long owner_id;

	public long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public int getStone_num() {
		return stone_num;
	}

	public void setStone_num(int stone_num) {
		this.stone_num = stone_num;
	}

	public long getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(long equipment_id) {
		this.equipment_id = equipment_id;
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

	public int getDouble_hit() {
		return double_hit;
	}

	public void setDouble_hit(int double_hit) {
		this.double_hit = double_hit;
	}

	public int getTenacity() {
		return tenacity;
	}

	public void setTenacity(int tenacity) {
		this.tenacity = tenacity;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	public int getParry() {
		return parry;
	}

	public void setParry(int parry) {
		this.parry = parry;
	}

	public int getPuncture() {
		return puncture;
	}

	public void setPuncture(int puncture) {
		this.puncture = puncture;
	}

	public Equipment_extra() {

	}

	public Equipment_extra(Equipment_extra ee, byte action) {
		setAction(action);
		this.equipment_id = ee.getEquipment_id();
		this.stone_num = ee.getStone_num();
		this.hp = ee.getHp();
		this.atk = ee.getAtk();
		this.w_def = ee.getW_def();
		this.n_def = ee.getN_def();
		this.double_hit = ee.getDouble_hit();
		this.tenacity = ee.getTenacity();
		this.hit = ee.getHit();
		this.miss = ee.getMiss();
		this.parry = ee.getParry();
		this.puncture = ee.getPuncture();
	}

}
