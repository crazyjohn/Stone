package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class VipGiftTemplet implements Serializable{
	public static final String Path = "其他系统\\VIP\\VIP礼包.xlsx";
	
	private int VipGiftId;//VIP礼包ID
	private String giftName;//礼包名称
	private int primeCost;//VIP礼包原始元宝价格
	private int nowPrice;//VIP礼包当前元宝价格
	private String buyNum;//每日可购买物品及数量上限
	private String describe;//描述
	public int getVipGiftId() {
		return VipGiftId;
	}
	public void setVipGiftId(int vipGiftId) {
		VipGiftId = vipGiftId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public int getPrimeCost() {
		return primeCost;
	}
	public void setPrimeCost(int primeCost) {
		this.primeCost = primeCost;
	}
	public int getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(int nowPrice) {
		this.nowPrice = nowPrice;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public static String getPath() {
		return Path;
	}
	
}	
