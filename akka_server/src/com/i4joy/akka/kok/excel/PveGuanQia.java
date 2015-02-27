package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PveGuanQia implements Serializable{
	public static final String Path = "PVE系统\\关卡表.xlsx";
	
	private int guanQiaId;//关卡ID
	private int chapterId;//章节ID
	private int tiaoZhanNum;//每日挑战次数
	private int guanQiaHard;//关卡难度
	private int tongQianReward;//铜钱奖励
	private int experienceReward;//阅历奖励
	private int zhenXingId;//阵型ID
	private int specialJuQingId;//特殊剧情阵型ID
	private String toolsReward;//随机道具奖励
	private String tianJiangBaoWu;//天降宝物奖励
	private String describe;//描述
	private String onceReward;//一次奖励

	public String getOnceReward() {
		return onceReward;
	}
	public void setOnceReward(String onceReward) {
		this.onceReward = onceReward;
	}
	public int getGuanQiaId() {
		return guanQiaId;
	}
	public void setGuanQiaId(int guanQiaId) {
		this.guanQiaId = guanQiaId;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getTiaoZhanNum() {
		return tiaoZhanNum;
	}
	public void setTiaoZhanNum(int tiaoZhanNum) {
		this.tiaoZhanNum = tiaoZhanNum;
	}
	public int getGuanQiaHard() {
		return guanQiaHard;
	}
	public void setGuanQiaHard(int guanQiaHard) {
		this.guanQiaHard = guanQiaHard;
	}
	public int getTongQianReward() {
		return tongQianReward;
	}
	public void setTongQianReward(int tongQianReward) {
		this.tongQianReward = tongQianReward;
	}
	public int getExperienceReward() {
		return experienceReward;
	}
	public void setExperienceReward(int experienceReward) {
		this.experienceReward = experienceReward;
	}
	public int getZhenXingId() {
		return zhenXingId;
	}
	public void setZhenXingId(int zhenXingId) {
		this.zhenXingId = zhenXingId;
	}
	public String getToolsReward() {
		return toolsReward;
	}
	public void setToolsReward(String toolsReward) {
		this.toolsReward = toolsReward;
	}
	public String getTianJiangBaoWu() {
		return tianJiangBaoWu;
	}
	public void setTianJiangBaoWu(String tianJiangBaoWu) {
		this.tianJiangBaoWu = tianJiangBaoWu;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public int getSpecialJuQingId() {
		return specialJuQingId;
	}
	public void setSpecialJuQingId(int specialJuQingId) {
		this.specialJuQingId = specialJuQingId;
	}
	public static String getPath() {
		return Path;
	}
	
}
