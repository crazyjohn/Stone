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
 * 侠客经脉模板
 * @author Administrator
 *
 */
public class MercenaryMeridiansTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357313464265725153L;
	
	private int level;
	
	private String name;
	
	private int starsNeeded;
	
	private int fee;
	
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
	public MercenaryMeridiansTemplet() {
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStarsNeeded() {
		return starsNeeded;
	}

	public void setStarsNeeded(int starsNeeded) {
		this.starsNeeded = starsNeeded;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
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
	
	public void addCurrentLevelProperty(MercenaryPropertyValue mpv){
		this.currentLevelPropsValue.put(mpv.getMpe(), mpv);
	}
	
	public void addTotalProperty(MercenaryPropertyValue mpv){
		this.totalPropsValue.put(mpv.getMpe(), mpv);
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.totalPropsValue.values();
	}
}
