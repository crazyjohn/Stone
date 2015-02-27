package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class PvpBaoWuQiangDuo implements Serializable{
	public static final String Path = "PVP系统\\宝物抢夺几率表.xlsx";
	
	private int baoWuId;//宝物ID
	private String baoWuName;//宝物名称
	private int getBaoWu;//抢夺机器人获得宝物几率
	public int getBaoWuId() {
		return baoWuId;
	}
	public void setBaoWuId(int baoWuId) {
		this.baoWuId = baoWuId;
	}
	public String getBaoWuName() {
		return baoWuName;
	}
	public void setBaoWuName(String baoWuName) {
		this.baoWuName = baoWuName;
	}
	public int getGetBaoWu() {
		return getBaoWu;
	}
	public void setGetBaoWu(int getBaoWu) {
		this.getBaoWu = getBaoWu;
	}
	public static String getPath() {
		return Path;
	}
}
