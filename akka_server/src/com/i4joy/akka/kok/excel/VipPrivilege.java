package com.i4joy.akka.kok.excel;

import java.io.Serializable;

public class VipPrivilege implements Serializable{
	public static final String Path = "其他系统\\VIP\\VIP特权数据表.xlsx";
	
	private int vipLevel;//VIP等级
	private String vipName;//VIP名称
	private int money;//充值累计RMB数量
	private int yuanBao;//充值累计元宝数量
	private int vipGiftId;//可购VIP礼包ID
	private int shopNum;//每时神秘商店可元宝刷新次数上限
	private int tiaoZhanNum;//每日可购买挑战塔次数上限
	private int least;//每次装备强化提升等级最小值
	private int biggest;//每次装备强化提升等级最大值
	private byte isAttack;//是否开启活动进击的魔神自动攻击
	private String describe;//描述
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getVipGiftId() {
		return vipGiftId;
	}
	public void setVipGiftId(int vipGiftId) {
		this.vipGiftId = vipGiftId;
	}
	public int getShopNum() {
		return shopNum;
	}
	public void setShopNum(int shopNum) {
		this.shopNum = shopNum;
	}
	public int getTiaoZhanNum() {
		return tiaoZhanNum;
	}
	public void setTiaoZhanNum(int tiaoZhanNum) {
		this.tiaoZhanNum = tiaoZhanNum;
	}
	public int getLeast() {
		return least;
	}
	public void setLeast(int least) {
		this.least = least;
	}
	public int getBiggest() {
		return biggest;
	}
	public void setBiggest(int biggest) {
		this.biggest = biggest;
	}
	public byte getIsAttack() {
		return isAttack;
	}
	public void setIsAttack(byte isAttack) {
		this.isAttack = isAttack;
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
	public int getYuanBao() {
		return yuanBao;
	}
	public void setYuanBao(int yuanBao) {
		this.yuanBao = yuanBao;
	}
	
}
