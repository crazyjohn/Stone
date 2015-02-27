package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class AmuletBaseProperty implements Serializable{
	public static final String Path = "宝物系统\\宝物基本属性表.xlsx";
	
	private int rewardId;//宝物ID
	private String rewardName;//宝物名称
	private int rewardType;//宝物类型
	private int starLevel;//星级
	private int pinLevel;//品级
	private byte isJingLian;//是否可精炼
	private String describe;//描述
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public int getRewardType() {
		return rewardType;
	}
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
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
	public byte getIsJingLian() {
		return isJingLian;
	}
	public void setIsJingLian(byte isJingLian) {
		this.isJingLian = isJingLian;
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
