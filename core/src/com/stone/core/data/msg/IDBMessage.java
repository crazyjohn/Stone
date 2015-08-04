package com.stone.core.data.msg;


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

	public long getId();
}
