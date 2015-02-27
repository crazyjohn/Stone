/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

import java.io.Serializable;

/**
 * 侠客属性，绝对值
 * @author Administrator
 *
 */
public class MercenaryPropertyValue extends MercenaryProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3561791455186113206L;
	
	private int value;

	/**
	 * 
	 */
	public MercenaryPropertyValue() {
		
	}
	
	public MercenaryPropertyValue(MercenaryPropertyEnum mpe,int value) {
		this.setMpe(mpe);
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public MercenaryPropertyValue clone(){
		return new MercenaryPropertyValue(this.getMpe(),this.value);
	}

	@Override
	public String toString() {
		return "MercenaryPropertyValue [mpe=" + this.getMpe() + ", value=" + value + "]";
	}
	
	

}
