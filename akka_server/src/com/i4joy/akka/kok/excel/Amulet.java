package com.i4joy.akka.kok.excel;

import java.io.Serializable;
import java.util.ArrayList;

public class Amulet implements Serializable {
	public static final String Path = "宝物系统\\宝物升级数值表\\";

	private short id;
	private ArrayList<AmuletLevel> levelList = new ArrayList<AmuletLevel>();
	private ArrayList<AmuletQuality> aualityList = new ArrayList<AmuletQuality>();

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public ArrayList<AmuletLevel> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<AmuletLevel> levelList) {
		this.levelList = levelList;
	}

	public ArrayList<AmuletQuality> getAualityList() {
		return aualityList;
	}

	public void setAualityList(ArrayList<AmuletQuality> aualityList) {
		this.aualityList = aualityList;
	}

}

class AmuletLevel implements Serializable {
	private byte level;// 强化等级
	private int pay_money;// 强化费用（铜钱）
	private int money;// 出售价格（铜钱）
	private int pay_exp;// 升级经验
	private int exp;// 融合经验
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
	private float hp_bili;// 生命
	private float atk_bili;// 攻击
	private float w_def_bili;// 外防
	private float n_def_bili;// 内防
	private float double_hit_bili;// 暴击
	private float renxing_bili;// 韧性
	private float hit_bili;// 命中
	private float miss_bili;// 闪避
	private float gedang_bili;// 格挡
	private float chuanchi_bili;// 穿刺

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
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

	public int getPay_exp() {
		return pay_exp;
	}

	public void setPay_exp(int pay_exp) {
		this.pay_exp = pay_exp;
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

	public float getHp_bili() {
		return hp_bili;
	}

	public void setHp_bili(float hp_bili) {
		this.hp_bili = hp_bili;
	}

	public float getAtk_bili() {
		return atk_bili;
	}

	public void setAtk_bili(float atk_bili) {
		this.atk_bili = atk_bili;
	}

	public float getW_def_bili() {
		return w_def_bili;
	}

	public void setW_def_bili(float w_def_bili) {
		this.w_def_bili = w_def_bili;
	}

	public float getN_def_bili() {
		return n_def_bili;
	}

	public void setN_def_bili(float n_def_bili) {
		this.n_def_bili = n_def_bili;
	}

	public float getDouble_hit_bili() {
		return double_hit_bili;
	}

	public void setDouble_hit_bili(float double_hit_bili) {
		this.double_hit_bili = double_hit_bili;
	}

	public float getRenxing_bili() {
		return renxing_bili;
	}

	public void setRenxing_bili(float renxing_bili) {
		this.renxing_bili = renxing_bili;
	}

	public float getHit_bili() {
		return hit_bili;
	}

	public void setHit_bili(float hit_bili) {
		this.hit_bili = hit_bili;
	}

	public float getMiss_bili() {
		return miss_bili;
	}

	public void setMiss_bili(float miss_bili) {
		this.miss_bili = miss_bili;
	}

	public float getGedang_bili() {
		return gedang_bili;
	}

	public void setGedang_bili(float gedang_bili) {
		this.gedang_bili = gedang_bili;
	}

	public float getChuanchi_bili() {
		return chuanchi_bili;
	}

	public void setChuanchi_bili(float chuanchi_bili) {
		this.chuanchi_bili = chuanchi_bili;
	}

}

class AmuletQuality implements Serializable {
	private byte level;// 精炼等级
	private int pay_money;// 精炼费用（铜钱）
	private String pay_items;// 精炼道具
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
	private float hp_bili;// 生命
	private float atk_bili;// 攻击
	private float w_def_bili;// 外防
	private float n_def_bili;// 内防
	private float double_hit_bili;// 暴击
	private float renxing_bili;// 韧性
	private float hit_bili;// 命中
	private float miss_bili;// 闪避
	private float gedang_bili;// 格挡
	private float chuanchi_bili;// 穿刺

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public int getPay_money() {
		return pay_money;
	}

	public void setPay_money(int pay_money) {
		this.pay_money = pay_money;
	}

	

	public String getPay_items() {
		return pay_items;
	}

	public void setPay_items(String pay_items) {
		this.pay_items = pay_items;
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

	public float getHp_bili() {
		return hp_bili;
	}

	public void setHp_bili(float hp_bili) {
		this.hp_bili = hp_bili;
	}

	public float getAtk_bili() {
		return atk_bili;
	}

	public void setAtk_bili(float atk_bili) {
		this.atk_bili = atk_bili;
	}

	public float getW_def_bili() {
		return w_def_bili;
	}

	public void setW_def_bili(float w_def_bili) {
		this.w_def_bili = w_def_bili;
	}

	public float getN_def_bili() {
		return n_def_bili;
	}

	public void setN_def_bili(float n_def_bili) {
		this.n_def_bili = n_def_bili;
	}

	public float getDouble_hit_bili() {
		return double_hit_bili;
	}

	public void setDouble_hit_bili(float double_hit_bili) {
		this.double_hit_bili = double_hit_bili;
	}

	public float getRenxing_bili() {
		return renxing_bili;
	}

	public void setRenxing_bili(float renxing_bili) {
		this.renxing_bili = renxing_bili;
	}

	public float getHit_bili() {
		return hit_bili;
	}

	public void setHit_bili(float hit_bili) {
		this.hit_bili = hit_bili;
	}

	public float getMiss_bili() {
		return miss_bili;
	}

	public void setMiss_bili(float miss_bili) {
		this.miss_bili = miss_bili;
	}

	public float getGedang_bili() {
		return gedang_bili;
	}

	public void setGedang_bili(float gedang_bili) {
		this.gedang_bili = gedang_bili;
	}

	public float getChuanchi_bili() {
		return chuanchi_bili;
	}

	public void setChuanchi_bili(float chuanchi_bili) {
		this.chuanchi_bili = chuanchi_bili;
	}

}
