package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class EquipmentBaseQuality implements Serializable{
	public static final String Path = "装备系统\\装备基本属性表.xlsx";
	
	private int equipmentId;//装备ID
	private String equipmentName;//装备名称
	private int starLevel;//星级
	private int pinLevel;//品级
	private int part;//部位
	private int suitId;//套装ID
	private byte isXiLian;//是否可洗练
	private byte isRongLian;//是否可熔炼
	private int life;//生命
	private int attack;//攻击
	private int outside;//外防
	private int inside;//内防
	private int lifeAdd;//生命强化
	private int attackAdd;//攻击强化
	private int outsideAdd;//外防强化
	private int insideAdd;//内防强化
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public int getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
	public int getPinLevel() {
		return pinLevel;
	}
	public void setPinLevel(int pinLevel) {
		this.pinLevel = pinLevel;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public int getSuitId() {
		return suitId;
	}
	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}
	public byte getIsXiLian() {
		return isXiLian;
	}
	public void setIsXiLian(byte isXiLian) {
		this.isXiLian = isXiLian;
	}
	public byte getIsRongLian() {
		return isRongLian;
	}
	public void setIsRongLian(byte isRongLian) {
		this.isRongLian = isRongLian;
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
	public int getLifeAdd() {
		return lifeAdd;
	}
	public void setLifeAdd(int lifeAdd) {
		this.lifeAdd = lifeAdd;
	}
	public int getAttackAdd() {
		return attackAdd;
	}
	public void setAttackAdd(int attackAdd) {
		this.attackAdd = attackAdd;
	}
	public int getOutsideAdd() {
		return outsideAdd;
	}
	public void setOutsideAdd(int outsideAdd) {
		this.outsideAdd = outsideAdd;
	}
	public int getInsideAdd() {
		return insideAdd;
	}
	public void setInsideAdd(int insideAdd) {
		this.insideAdd = insideAdd;
	}
	public static String getPath() {
		return Path;
	}
	
}
