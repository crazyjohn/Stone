package com.stone.core.data.msg;

import java.io.Serializable;

/**
 * The db message;
 * 
 * @author crazyjohn
 *
 */
public interface IDBMessage {
	/**
	 * Get the db entity;
	 * 
	 * @return
	 */
	public Class<?> getEntityClass();
	
	public Serializable getId();
}
