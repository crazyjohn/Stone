/**
 * 
 */
package com.i4joy.akka.kok.monster.player.templet;

import java.io.Serializable;

/**
 * 角色初始化属性模板
 * @author Administrator
 *
 */
public class PlayerInitDataTemplet implements Serializable {
	
	/**
	 * 铜钱初始值
	 */
	private int money;
	
	/**
	 * 声望初始值
	 */
	private int reputation;
	
	/**
	 * 魂玉初始值
	 */
	private int jades;
	
	/**
	 * 经验初始值
	 */
	private int exp;
	
	/**
	 * 阅历初始值
	 */
	private int mercenaryExp;
	
	/**
	 * 当前体力
	 */
	private int currentPveStamina;
	
	/**
	 * 体力最大值
	 */
	private int maxPveStamina;
	
	/**
	 * 当前耐力
	 */
	private int currentPvpStamina;
	
	/**
	 * 耐力最大值
	 */
	private int maxPvpStamina;
	
	/**
	 * VIP等级
	 */
	private int vip;
	
	/**
	 * 充值元宝初始值
	 */
	private int chargeGold;
	
	/**
	 * 免费元宝初始值
	 */
	private int freeGold;
	
	/**
	 * 最大上阵数量
	 */
	private int maxLineup;
	
	/**
	 * 侠客仓库上限
	 */
	private int mercenaryStoreTop;
	
	/**
	 * 侠灵仓库上限
	 */
	private int ghostStoreTop;
	
	/**
	 * 装备仓库上限
	 */
	private int equipmentStoreTop;
	
	/**
	 * 装备碎片仓库上限
	 */
	private int equipmentPieceStoreTop;
	
	/**
	 * 道具仓库上限
	 */
	private int itemStoreTop;
	
	/**
	 * 宝物仓库上限
	 */
	private int treasureStoreTop;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -493725137991601684L;

	/**
	 * 
	 */
	public PlayerInitDataTemplet() {
		
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public int getJades() {
		return jades;
	}

	public void setJades(int jades) {
		this.jades = jades;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMercenaryExp() {
		return mercenaryExp;
	}

	public void setMercenaryExp(int mercenaryExp) {
		this.mercenaryExp = mercenaryExp;
	}

	public int getCurrentPveStamina() {
		return currentPveStamina;
	}

	public void setCurrentPveStamina(int currentPveStamina) {
		this.currentPveStamina = currentPveStamina;
	}

	public int getMaxPveStamina() {
		return maxPveStamina;
	}

	public void setMaxPveStamina(int maxPveStamina) {
		this.maxPveStamina = maxPveStamina;
	}

	public int getCurrentPvpStamina() {
		return currentPvpStamina;
	}

	public void setCurrentPvpStamina(int currentPvpStamina) {
		this.currentPvpStamina = currentPvpStamina;
	}

	public int getMaxPvpStamina() {
		return maxPvpStamina;
	}

	public void setMaxPvpStamina(int maxPvpStamina) {
		this.maxPvpStamina = maxPvpStamina;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getChargeGold() {
		return chargeGold;
	}

	public void setChargeGold(int chargeGold) {
		this.chargeGold = chargeGold;
	}

	public int getFreeGold() {
		return freeGold;
	}

	public void setFreeGold(int freeGold) {
		this.freeGold = freeGold;
	}

	public int getMaxLineup() {
		return maxLineup;
	}

	public void setMaxLineup(int maxLineup) {
		this.maxLineup = maxLineup;
	}

	public int getMercenaryStoreTop() {
		return mercenaryStoreTop;
	}

	public void setMercenaryStoreTop(int mercenaryStoreTop) {
		this.mercenaryStoreTop = mercenaryStoreTop;
	}

	public int getGhostStoreTop() {
		return ghostStoreTop;
	}

	public void setGhostStoreTop(int ghostStoreTop) {
		this.ghostStoreTop = ghostStoreTop;
	}

	public int getEquipmentStoreTop() {
		return equipmentStoreTop;
	}

	public void setEquipmentStoreTop(int equipmentStoreTop) {
		this.equipmentStoreTop = equipmentStoreTop;
	}

	public int getEquipmentPieceStoreTop() {
		return equipmentPieceStoreTop;
	}

	public void setEquipmentPieceStoreTop(int equipmentPieceStoreTop) {
		this.equipmentPieceStoreTop = equipmentPieceStoreTop;
	}

	public int getItemStoreTop() {
		return itemStoreTop;
	}

	public void setItemStoreTop(int itemStoreTop) {
		this.itemStoreTop = itemStoreTop;
	}

	public int getTreasureStoreTop() {
		return treasureStoreTop;
	}

	public void setTreasureStoreTop(int treasureStoreTop) {
		this.treasureStoreTop = treasureStoreTop;
	}

	@Override
	public String toString() {
		return "PlayerInitDataTemplet [money=" + money + ", reputation="
				+ reputation + ", jades=" + jades + ", exp=" + exp
				+ ", mercenaryExp=" + mercenaryExp + ", currentPveStamina="
				+ currentPveStamina + ", maxPveStamina=" + maxPveStamina
				+ ", currentPvpStamina=" + currentPvpStamina
				+ ", maxPvpStamina=" + maxPvpStamina + ", vip=" + vip
				+ ", chargeGold=" + chargeGold + ", freeGold=" + freeGold
				+ ", maxLineup=" + maxLineup + ", mercenaryStoreTop="
				+ mercenaryStoreTop + ", ghostStoreTop=" + ghostStoreTop
				+ ", equipmentStoreTop=" + equipmentStoreTop
				+ ", equipmentPieceStoreTop=" + equipmentPieceStoreTop
				+ ", itemStoreTop=" + itemStoreTop + ", treasureStoreTop="
				+ treasureStoreTop + "]";
	}
	
	
}
