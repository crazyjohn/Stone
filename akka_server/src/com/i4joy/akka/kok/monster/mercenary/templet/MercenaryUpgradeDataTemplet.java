/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MercenaryUpgradeDataTemplet implements Serializable {
	
	private int level;
	
	/**
	 * 费用
	 */
	private int fee;
	
	/**
	 * 所需经验
	 */
	private int expNeeded;
	
	/**
	 * 强化时能提供的经验
	 */
	private int expFromStrengthen;
	
	/**
	 * 炼化时返还的货币
	 */
	private int moneyFromSacrifice;
	
	/**
	 * 炼化时返还的经验
	 */
	private int expFromSacrifice;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1675651650267953163L;

	/**
	 * 
	 */
	public MercenaryUpgradeDataTemplet() {
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getExpNeeded() {
		return expNeeded;
	}

	public void setExpNeeded(int expNeeded) {
		this.expNeeded = expNeeded;
	}

	public int getExpFromStrengthen() {
		return expFromStrengthen;
	}

	public void setExpFromStrengthen(int expFromStrengthen) {
		this.expFromStrengthen = expFromStrengthen;
	}

	public int getMoneyFromSacrifice() {
		return moneyFromSacrifice;
	}

	public void setMoneyFromSacrifice(int moneyFromSacrifice) {
		this.moneyFromSacrifice = moneyFromSacrifice;
	}

	public int getExpFromSacrifice() {
		return expFromSacrifice;
	}

	public void setExpFromSacrifice(int expFromSacrifice) {
		this.expFromSacrifice = expFromSacrifice;
	}
	

}
