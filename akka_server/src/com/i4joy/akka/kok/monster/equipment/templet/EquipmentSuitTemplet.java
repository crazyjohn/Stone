package com.i4joy.akka.kok.monster.equipment.templet;

import java.util.HashMap;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

public class EquipmentSuitTemplet {
	private int suit_id;
	private int num;

	public void addCurrentLevelProperty(MercenaryPropertyValue mpv) {
		this.currentLevelPropsValue.put(mpv.getMpe(), mpv);
	}

	public void addTotalProperty(MercenaryPropertyValue mpv) {
		this.totalPropsValue.put(mpv.getMpe(), mpv);
	}

	/**
	 * 当前等级增加的属性，仅用作显示
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> currentLevelPropsValue = new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 增加的属性总值
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> totalPropsValue = new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	public int getSuit_id() {
		return suit_id;
	}

	public void setSuit_id(int suit_id) {
		this.suit_id = suit_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getCurrentLevelPropsValue() {
		return currentLevelPropsValue;
	}

	public void setCurrentLevelPropsValue(HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> currentLevelPropsValue) {
		this.currentLevelPropsValue = currentLevelPropsValue;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getTotalPropsValue() {
		return totalPropsValue;
	}

	public void setTotalPropsValue(HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> totalPropsValue) {
		this.totalPropsValue = totalPropsValue;
	}

}
