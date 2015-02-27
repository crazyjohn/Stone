package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class BoxReward implements Serializable{
	public static final String Path = "其他系统\\道具\\宝箱系统\\宝箱奖励列表.xlsx";
	
	private int toolsId;//道具ID
	private int boxID;//宝箱钥匙ID
	private int rewardType;//奖品类型
	private String reward;//奖品
	public int getToolsId() {
		return toolsId;
	}
	public void setToolsId(int toolsId) {
		this.toolsId = toolsId;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	public int getRewardType() {
		return rewardType;
	}
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
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
