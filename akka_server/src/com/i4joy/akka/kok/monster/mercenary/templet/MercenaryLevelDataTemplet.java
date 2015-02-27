/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * 侠客等级数据
 * @author Administrator
 *
 */
public class MercenaryLevelDataTemplet implements Serializable {
	
	private int level;
	
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 3238839631665963305L;

	/**
	 * 
	 */
	public MercenaryLevelDataTemplet() {
		
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

	public void setPropsValue(
			HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue) {
		this.propsValue = propsValue;
	}
	
	public void addProperty(MercenaryPropertyValue mpv){
		this.propsValue.put(mpv.getMpe(), mpv);
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.propsValue.values();
	}

}
