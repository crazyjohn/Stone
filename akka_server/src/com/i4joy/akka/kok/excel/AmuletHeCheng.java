package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class AmuletHeCheng implements Serializable{
	public static final String Path = "宝物系统\\宝物合成表.xlsx";
	
	private int rewardId;//宝物ID
	private int rewardPatch_1;//宝物残片1
	private int rewardPatch_2;//宝物残片2
	private int rewardPatch_3;//宝物残片3
	private int rewardPatch_4;//宝物残片4
	private int rewardPatch_5;//宝物残片5
	private int rewardPatch_6;//宝物残片6
	private String rewardName;//宝物名称
	private String patch_1Name;//残片1名称
	private String patch_2Name;//残片2名称
	private String patch_3Name;//残片3名称
	private String patch_4Name;//残片4名称
	private String patch_5Name;//残片5名称
	private String patch_6Name;//残片6名称
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public int getRewardPatch_1() {
		return rewardPatch_1;
	}
	public void setRewardPatch_1(int rewardPatch_1) {
		this.rewardPatch_1 = rewardPatch_1;
	}
	public int getRewardPatch_2() {
		return rewardPatch_2;
	}
	public void setRewardPatch_2(int rewardPatch_2) {
		this.rewardPatch_2 = rewardPatch_2;
	}
	public int getRewardPatch_3() {
		return rewardPatch_3;
	}
	public void setRewardPatch_3(int rewardPatch_3) {
		this.rewardPatch_3 = rewardPatch_3;
	}
	public int getRewardPatch_4() {
		return rewardPatch_4;
	}
	public void setRewardPatch_4(int rewardPatch_4) {
		this.rewardPatch_4 = rewardPatch_4;
	}
	public int getRewardPatch_5() {
		return rewardPatch_5;
	}
	public void setRewardPatch_5(int rewardPatch_5) {
		this.rewardPatch_5 = rewardPatch_5;
	}
	public int getRewardPatch_6() {
		return rewardPatch_6;
	}
	public void setRewardPatch_6(int rewardPatch_6) {
		this.rewardPatch_6 = rewardPatch_6;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public String getPatch_1Name() {
		return patch_1Name;
	}
	public void setPatch_1Name(String patch_1Name) {
		this.patch_1Name = patch_1Name;
	}
	public String getPatch_2Name() {
		return patch_2Name;
	}
	public void setPatch_2Name(String patch_2Name) {
		this.patch_2Name = patch_2Name;
	}
	public String getPatch_3Name() {
		return patch_3Name;
	}
	public void setPatch_3Name(String patch_3Name) {
		this.patch_3Name = patch_3Name;
	}
	public String getPatch_4Name() {
		return patch_4Name;
	}
	public void setPatch_4Name(String patch_4Name) {
		this.patch_4Name = patch_4Name;
	}
	public String getPatch_5Name() {
		return patch_5Name;
	}
	public void setPatch_5Name(String patch_5Name) {
		this.patch_5Name = patch_5Name;
	}
	public String getPatch_6Name() {
		return patch_6Name;
	}
	public void setPatch_6Name(String patch_6Name) {
		this.patch_6Name = patch_6Name;
	}
	public static String getPath() {
		return Path;
	}
	
}
