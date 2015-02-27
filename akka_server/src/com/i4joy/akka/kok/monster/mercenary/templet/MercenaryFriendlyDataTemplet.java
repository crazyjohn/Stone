/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * 侠客情谊数据模板
 * @author Administrator
 *
 */
public class MercenaryFriendlyDataTemplet implements Serializable {
	
	private int level;
	
	/**
	 * 升级所需经验
	 */
	private int expNeeded;
	
	/**
	 * 直接升级概率
	 */
	private int criticalPercent;
	
	/**
	 * 当前等级增加的属性，仅用作显示
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> currentLevelPropsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();
	
	/**
	 * 增加的属性总值
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> totalPropsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 4676020786751491339L;

	/**
	 * 
	 */
	public MercenaryFriendlyDataTemplet() {
		
	}
	
	public void addCurrentLevelProperty(MercenaryPropertyValue mpv){
		this.currentLevelPropsValue.put(mpv.getMpe(), mpv);
	}
	
	public void addTotalProperty(MercenaryPropertyValue mpv){
		this.totalPropsValue.put(mpv.getMpe(), mpv);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExpNeeded() {
		return expNeeded;
	}

	public void setExpNeeded(int expNeeded) {
		this.expNeeded = expNeeded;
	}

	public int getCriticalPercent() {
		return criticalPercent;
	}

	public void setCriticalPercent(int criticalPercent) {
		this.criticalPercent = criticalPercent;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getCurrentLevelPropsValue() {
		return currentLevelPropsValue;
	}

	public void setCurrentLevelPropsValue(
			HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> currentLevelPropsValue) {
		this.currentLevelPropsValue = currentLevelPropsValue;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getTotalPropsValue() {
		return totalPropsValue;
	}

	public void setTotalPropsValue(
			HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> totalPropsValue) {
		this.totalPropsValue = totalPropsValue;
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.totalPropsValue.values();
	}
	
}
