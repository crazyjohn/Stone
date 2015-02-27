package com.ump.model;

import java.io.Serializable;

public abstract class DBCache implements Serializable{

	public static final byte SELECT = 1;
	public static final byte INSERT = 2;
	public static final byte UPDATE = 3;
	public static final byte FAIL = 4;

	private long cacheTime;
	private byte action;
	
	public byte getAction() {
		return action;
	}

	public void setAction(byte action) {
		this.action = action;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}
}
