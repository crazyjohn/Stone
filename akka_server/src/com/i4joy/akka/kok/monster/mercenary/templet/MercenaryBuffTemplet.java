/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

import com.i4joy.akka.kok.monster.ProtoBuffFileElement;

/**
 * BUFF模板
 * @author Administrator
 *
 */
public class MercenaryBuffTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 942100359536758543L;
	
	private int id;
	
	private String desc;
	
	private int buffType;
	
	private float value;
	
	private int effect;

	/**
	 * 
	 */
	public MercenaryBuffTemplet() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getBuffType() {
		return buffType;
	}

	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

}
