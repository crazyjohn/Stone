package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class AmuletSkill implements Serializable{
	public static final String Path = "宝物系统\\宝物技能表.xlsx";
	
	private int rewardSkillId;//宝物技能ID
	private String rewardName;//宝物名称
	private int jieSuoLevel;//解锁等级
	private int life;//生命值加成（%）
	private int attack;//攻击加成（%）
	private int outside;//外功防御加成（%）
	private int inside;//内功防御加成（%）
	private int baoJi;//暴击值加成（%）
	private int renXing;//韧性值加成（%）
	private int mingZhong;//命中值加成（%）
	private int shanBi;//闪避值加成（%）
	private int geDang;//格挡值加成（%）
	private int chuanCi;//穿刺值加成（%）
	private String describe;//描述
	public int getRewardSkillId() {
		return rewardSkillId;
	}
	public void setRewardSkillId(int rewardSkillId) {
		this.rewardSkillId = rewardSkillId;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public int getJieSuoLevel() {
		return jieSuoLevel;
	}
	public void setJieSuoLevel(int jieSuoLevel) {
		this.jieSuoLevel = jieSuoLevel;
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public static String getPath() {
		return Path;
	}
}
