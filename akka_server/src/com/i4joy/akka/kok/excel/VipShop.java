package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class VipShop implements Serializable{
	public static final String Path = "其他系统\\VIP\\VIP商城.xlsx";
	
	private int vipLevel;//VIP等级
	private String buyNum;//每日可购买物品及数量上限
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public static String getPath() {
		return Path;
	}
	
}
