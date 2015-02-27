/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;

/**
 * 侠灵模板
 * @author Administrator
 *
 */
public class MercenaryGhostTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6824395651887917226L;
	
	private long id;
	
	private long mercenaryId;
	
	private String desc;

	/**
	 * 
	 */
	public MercenaryGhostTemplet() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
