package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class OtherFirstPay implements Serializable{
	public static final String Path = "其他系统\\奖励系统\\首冲奖励数值表.xlsx";
	
	private int level;//角色等级
	private String reward;//奖励物品及数量
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public static String getPath() {
		return Path;
	}
}
