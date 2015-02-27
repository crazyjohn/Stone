/**
 * 
 */
package com.i4joy.akka.kok.monster.item;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class IdNumPair implements Serializable {
	
	private long id;
	
	private int num;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9005707634613547824L;

	/**
	 * 
	 */
	public IdNumPair() {
		
	}
	
	public IdNumPair(long id,int num) {
		this.id=id;
		this.num=num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
