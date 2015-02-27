package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class OtherCheckReward implements Serializable{
	public static final String Path = "其他系统\\奖励系统\\签到礼包数据表.xlsx";
	
	private int days;//签到天数
	private String reward;//奖励物品及数量
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
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
