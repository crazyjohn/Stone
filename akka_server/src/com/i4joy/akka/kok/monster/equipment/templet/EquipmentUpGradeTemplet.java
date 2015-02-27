package com.i4joy.akka.kok.monster.equipment.templet;

import java.util.Collection;
import java.util.HashMap;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

public class EquipmentUpGradeTemplet {
	public int level;
	/**
	 * 属性列表
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();
	public void addProperty(MercenaryPropertyValue mpv){
		this.propsValue.put(mpv.getMpe(), mpv);
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getPropsValue() {
		return propsValue;
	}
	public void setPropsValue(HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue) {
		this.propsValue = propsValue;
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.propsValue.values();
	}
	
}
