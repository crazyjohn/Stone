package com.stone.core.entity;

import java.io.Serializable;

/**
 * Human sub entity;
 * 
 * @author crazyjohn
 *
 * @param <ID>
 */
public interface IHumanSubEntity<ID extends Serializable> extends IEntity {
	/**
	 * Get human id;
	 * 
	 * @return
	 */
	public long getHumanId();
}
