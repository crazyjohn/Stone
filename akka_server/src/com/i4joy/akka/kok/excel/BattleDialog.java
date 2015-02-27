package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class BattleDialog implements Serializable {
	public static final String Path = "PVE系统\\关卡与剧情对应表.xlsx";
	private int battleId;// 关卡ID
	private String battleStartDialog;// 进入关卡前剧情对话
	private String battleIngDialog;// 战斗开始前剧情对话
	private String battleEndDialog;// 战斗结束后剧情对话

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public String getBattleStartDialog() {
		return battleStartDialog;
	}

	public void setBattleStartDialog(String battleStartDialog) {
		this.battleStartDialog = battleStartDialog;
	}

	public String getBattleIngDialog() {
		return battleIngDialog;
	}

	public void setBattleIngDialog(String battleIngDialog) {
		this.battleIngDialog = battleIngDialog;
	}

	public String getBattleEndDialog() {
		return battleEndDialog;
	}

	public void setBattleEndDialog(String battleEndDialog) {
		this.battleEndDialog = battleEndDialog;
	}

}
