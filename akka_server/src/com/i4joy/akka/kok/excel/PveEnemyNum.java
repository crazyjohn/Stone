package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PveEnemyNum implements Serializable{
	public static final String Path = "PVE系统\\敌人数值表.xlsx";
	
	private int enemtyId;//敌人ID
	private int mercenaryId;//侠客ID
	private String mercenaryName;//侠客名称
	private int level;//等级
	private int jinJieLevel;//进阶等级
	private int equipId;//装备ID
	private int equipQiangHuaLevel;//装备强化等级
	private int valuedId;//宝物ID
	private int valuedQiangHuaLevel;//宝物强化等级
	private int valuedJingLianLevel;//宝物精炼等级
	private int life;//生命
	private int attack;//攻击
	private int outside;//外防
	private int inside;//内防
	private int baoJi;//暴击
	private int renXing;//韧性
	private int mingZhong;//命中
	private int shanBi;//闪避
	private int geDang;//格挡
	public int getEnemtyId() {
		return enemtyId;
	}
	public void setEnemtyId(int enemtyId) {
		this.enemtyId = enemtyId;
	}
	public int getMercenaryId() {
		return mercenaryId;
	}
	public void setMercenaryId(int mercenaryId) {
		this.mercenaryId = mercenaryId;
	}
	public String getMercenaryName() {
		return mercenaryName;
	}
	public void setMercenaryName(String mercenaryName) {
		this.mercenaryName = mercenaryName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getJinJieLevel() {
		return jinJieLevel;
	}
	public void setJinJieLevel(int jinJieLevel) {
		this.jinJieLevel = jinJieLevel;
	}
	public int getEquipId() {
		return equipId;
	}
	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}
	public int getEquipQiangHuaLevel() {
		return equipQiangHuaLevel;
	}
	public void setEquipQiangHuaLevel(int equipQiangHuaLevel) {
		this.equipQiangHuaLevel = equipQiangHuaLevel;
	}
	public int getValuedId() {
		return valuedId;
	}
	public void setValuedId(int valuedId) {
		this.valuedId = valuedId;
	}
	public int getValuedQiangHuaLevel() {
		return valuedQiangHuaLevel;
	}
	public void setValuedQiangHuaLevel(int valuedQiangHuaLevel) {
		this.valuedQiangHuaLevel = valuedQiangHuaLevel;
	}
	public int getValuedJingLianLevel() {
		return valuedJingLianLevel;
	}
	public void setValuedJingLianLevel(int valuedJingLianLevel) {
		this.valuedJingLianLevel = valuedJingLianLevel;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getOutside() {
		return outside;
	}
	public void setOutside(int outside) {
		this.outside = outside;
	}
	public int getInside() {
		return inside;
	}
	public void setInside(int inside) {
		this.inside = inside;
	}
	public int getBaoJi() {
		return baoJi;
	}
	public void setBaoJi(int baoJi) {
		this.baoJi = baoJi;
	}
	public int getRenXing() {
		return renXing;
	}
	public void setRenXing(int renXing) {
		this.renXing = renXing;
	}
	public int getMingZhong() {
		return mingZhong;
	}
	public void setMingZhong(int mingZhong) {
		this.mingZhong = mingZhong;
	}
	public int getShanBi() {
		return shanBi;
	}
	public void setShanBi(int shanBi) {
		this.shanBi = shanBi;
	}
	public int getGeDang() {
		return geDang;
	}
	public void setGeDang(int geDang) {
		this.geDang = geDang;
	}
	public int getChuanCi() {
		return chuanCi;
	}
	public void setChuanCi(int chuanCi) {
		this.chuanCi = chuanCi;
	}
	public static String getPath() {
		return Path;
	}
	private int chuanCi;//穿刺

}
