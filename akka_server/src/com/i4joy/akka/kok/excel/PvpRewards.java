package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PvpRewards implements Serializable{
	public static final String Path = "PVP系统\\PVP随机奖励表.xlsx";
	
	private int roleLevel;//角色等级
	private String toolsReward;//随机道具奖励
	private int gaiLv;//刷出概率
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	public String getToolsReward() {
		return toolsReward;
	}
	public void setToolsReward(String toolsReward) {
		this.toolsReward = toolsReward;
	}
	public int getGaiLv() {
		return gaiLv;
	}
	public void setGaiLv(int gaiLv) {
		this.gaiLv = gaiLv;
	}
	public static String getPath() {
		return Path;
	}
}
