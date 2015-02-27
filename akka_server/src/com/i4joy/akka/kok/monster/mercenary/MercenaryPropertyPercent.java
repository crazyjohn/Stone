/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

import java.io.Serializable;

/**
 * 侠客属性，百分比
 * @author Administrator
 *
 */
public class MercenaryPropertyPercent extends MercenaryProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1881173387951053194L;
	
	private float value;

	/**
	 * 
	 */
	public MercenaryPropertyPercent() {
	}
	
	public MercenaryPropertyPercent(MercenaryPropertyEnum mpe,float value) {
		this.setMpe(mpe);
		this.value=value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public MercenaryPropertyPercent clone(){
		return new MercenaryPropertyPercent(this.getMpe(),this.value);
	}

	@Override
	public String toString() {
		return "MercenaryPropertyPercent [value=" + value + ", getMpe()="
				+ getMpe() + "]";
	}
	

}
