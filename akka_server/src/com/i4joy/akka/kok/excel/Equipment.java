package com.i4joy.akka.kok.excel;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipment implements Serializable {
	public static final String Path = "装备系统\\装备数值升级表\\";
	private short id;
	private ArrayList<EquipmentLevel> levelList = new ArrayList<EquipmentLevel>();
	private ArrayList<EQuality> qualityList = new ArrayList<EQuality>();

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public ArrayList<EquipmentLevel> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<EquipmentLevel> levelList) {
		this.levelList = levelList;
	}

	public ArrayList<EQuality> getQualityList() {
		return qualityList;
	}

	public void setQualityList(ArrayList<EQuality> qualityList) {
		this.qualityList = qualityList;
	}

}

class EquipmentLevel implements Serializable {
	private int level;// 强化等级
	private int pay_money;// 强化费用（铜钱）
	private int money;// 出售/炼化回收的铜钱数量
	private int stone;// 炼化获得的洗练石数量
	private int hp;// 生命
	private int atk;// 攻击
	private int w_def;// 外防
	private int n_def;// 内防
	private int double_hit;// 暴击
	private int renxing;// 韧性
	private int hit;// 命中
	private int miss;// 闪避
	private int gedang;// 格挡
	private int chuanchi;// 穿刺
	private int hp_limit;// 生命洗练增加值上限
	private int atk_limit;// 攻击洗练增加值上限
	private int w_def_limit;// 外防洗练增加值上限
	private int n_def_limit;// 内防洗练增加值上限
	private int double_hit_limit;// 暴击洗练增加值上限
	private int renxing_limit;// 韧性洗练增加值上限
	private int hit_limit;// 命中洗练增加值上限
	private int miss_limit;// 闪避洗练增加值上限
	private int gedang_limit;// 格挡洗练增加值上限
	private int chuanchi_limit;// 穿刺洗练增加值上限

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPay_money() {
		return pay_money;
	}

	public void setPay_money(int pay_money) {
		this.pay_money = pay_money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getStone() {
		return stone;
	}

	public void setStone(int stone) {
		this.stone = stone;
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

	public int getRenxing() {
		return renxing;
	}

	public void setRenxing(int renxing) {
		this.renxing = renxing;
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

	public int getGedang() {
		return gedang;
	}

	public void setGedang(int gedang) {
		this.gedang = gedang;
	}

	public int getChuanchi() {
		return chuanchi;
	}

	public void setChuanchi(int chuanchi) {
		this.chuanchi = chuanchi;
	}

	public int getHp_limit() {
		return hp_limit;
	}

	public void setHp_limit(int hp_limit) {
		this.hp_limit = hp_limit;
	}

	public int getAtk_limit() {
		return atk_limit;
	}

	public void setAtk_limit(int atk_limit) {
		this.atk_limit = atk_limit;
	}

	public int getW_def_limit() {
		return w_def_limit;
	}

	public void setW_def_limit(int w_def_limit) {
		this.w_def_limit = w_def_limit;
	}

	public int getN_def_limit() {
		return n_def_limit;
	}

	public void setN_def_limit(int n_def_limit) {
		this.n_def_limit = n_def_limit;
	}

	public int getDouble_hit_limit() {
		return double_hit_limit;
	}

	public void setDouble_hit_limit(int double_hit_limit) {
		this.double_hit_limit = double_hit_limit;
	}

	public int getRenxing_limit() {
		return renxing_limit;
	}

	public void setRenxing_limit(int renxing_limit) {
		this.renxing_limit = renxing_limit;
	}

	public int getHit_limit() {
		return hit_limit;
	}

	public void setHit_limit(int hit_limit) {
		this.hit_limit = hit_limit;
	}

	public int getMiss_limit() {
		return miss_limit;
	}

	public void setMiss_limit(int miss_limit) {
		this.miss_limit = miss_limit;
	}

	public int getGedang_limit() {
		return gedang_limit;
	}

	public void setGedang_limit(int gedang_limit) {
		this.gedang_limit = gedang_limit;
	}

	public int getChuanchi_limit() {
		return chuanchi_limit;
	}

	public void setChuanchi_limit(int chuanchi_limit) {
		this.chuanchi_limit = chuanchi_limit;
	}

}

class EQuality implements Serializable {
	private String propertyName;
	private int initValue;
	private int normalMin;
	private int normalMax;
	private int middleMin;
	private int middleMax;
	private int bigMin;
	private int bigMax;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getInitValue() {
		return initValue;
	}

	public void setInitValue(int initValue) {
		this.initValue = initValue;
	}

	public int getNormalMin() {
		return normalMin;
	}

	public void setNormalMin(int normalMin) {
		this.normalMin = normalMin;
	}

	public int getNormalMax() {
		return normalMax;
	}

	public void setNormalMax(int normalMax) {
		this.normalMax = normalMax;
	}

	public int getMiddleMin() {
		return middleMin;
	}

	public void setMiddleMin(int middleMin) {
		this.middleMin = middleMin;
	}

	public int getMiddleMax() {
		return middleMax;
	}

	public void setMiddleMax(int middleMax) {
		this.middleMax = middleMax;
	}

	public int getBigMin() {
		return bigMin;
	}

	public void setBigMin(int bigMin) {
		this.bigMin = bigMin;
	}

	public int getBigMax() {
		return bigMax;
	}

	public void setBigMax(int bigMax) {
		this.bigMax = bigMax;
	}

}
