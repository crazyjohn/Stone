package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class EquipmentQuality implements Serializable{
	public static final String Path = "装备系统\\套装属性表.xlsx";
	
	private int suitId;//套装ID
	private int suitNum;//套装数量
	private int life;//生命
	private int attack;//攻击
	private int outside;//外防
	private int inside;//内防
	private int lifeSum;//生命总和
	private int attackSum;//攻击总和
	private int outsideSum;//外防总和
	private int insideSum;//内防总和
	public int getSuitId() {
		return suitId;
	}
	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}
	public int getSuitNum() {
		return suitNum;
	}
	public void setSuitNum(int suitNum) {
		this.suitNum = suitNum;
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
	public int getLifeSum() {
		return lifeSum;
	}
	public void setLifeSum(int lifeSum) {
		this.lifeSum = lifeSum;
	}
	public int getAttackSum() {
		return attackSum;
	}
	public void setAttackSum(int attackSum) {
		this.attackSum = attackSum;
	}
	public int getOutsideSum() {
		return outsideSum;
	}
	public void setOutsideSum(int outsideSum) {
		this.outsideSum = outsideSum;
	}
	public int getInsideSum() {
		return insideSum;
	}
	public void setInsideSum(int insideSum) {
		this.insideSum = insideSum;
	}
	public static String getPath() {
		return Path;
	}
	
	
}
