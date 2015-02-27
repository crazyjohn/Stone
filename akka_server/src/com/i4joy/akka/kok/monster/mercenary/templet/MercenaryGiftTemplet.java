/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

/**
 * 侠客礼品模板
 * @author Administrator
 *
 */
public class MercenaryGiftTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3657487790536344715L;
	
	private long id;
	
	private String name;
	
	private int stars;
	
	private int friendlyValue;
	
	private String desc;

	/**
	 * 
	 */
	public MercenaryGiftTemplet() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getFriendlyValue() {
		return friendlyValue;
	}

	public void setFriendlyValue(int friendlyValue) {
		this.friendlyValue = friendlyValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
