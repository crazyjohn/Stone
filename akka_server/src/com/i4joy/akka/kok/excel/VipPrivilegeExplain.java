package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class VipPrivilegeExplain implements Serializable{
	public static final String Path = "其他系统\\VIP\\VIP特权说明表.xlsx";
	
	private String vipLevel;//VIP等级
	private int money;//充值额度（RMB）
	private String vipPrivilege;//VIP特权
	private String vipGift;//VIP礼包内容
	private int oldVipGift;//VIP礼包原价
	private int newVipGift;//VIP礼包现价
	
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getVipPrivilege() {
		return vipPrivilege;
	}
	public void setVipPrivilege(String vipPrivilege) {
		this.vipPrivilege = vipPrivilege;
	}
	public String getVipGift() {
		return vipGift;
	}
	public void setVipGift(String vipGift) {
		this.vipGift = vipGift;
	}
	public int getOldVipGift() {
		return oldVipGift;
	}
	public void setOldVipGift(int oldVipGift) {
		this.oldVipGift = oldVipGift;
	}
	public int getNewVipGift() {
		return newVipGift;
	}
	public void setNewVipGift(int newVipGift) {
		this.newVipGift = newVipGift;
	}
	public static String getPath() {
		return Path;
	}
	
}
