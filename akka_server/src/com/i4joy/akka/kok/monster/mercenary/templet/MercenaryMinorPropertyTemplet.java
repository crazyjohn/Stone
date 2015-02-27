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
 * 侠客次要属性模板
 * @author Administrator
 *
 */
public class MercenaryMinorPropertyTemplet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7450841980774657123L;

	private int id;
	
	/**
	 * 属性列表
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();

	/**
	 * 
	 */
	public MercenaryMinorPropertyTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
