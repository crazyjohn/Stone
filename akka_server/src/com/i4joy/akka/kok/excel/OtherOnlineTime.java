package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class OtherOnlineTime implements Serializable{
	public static final String Path = "其他系统\\奖励系统\\在线礼包数据表.xlsx";
	
	private int onlineTime;//在线时间（分钟）
	private String reward;//奖励物品及数量
	public int getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
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
