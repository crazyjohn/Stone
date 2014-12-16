package com.stone.core.entity;

import java.io.Serializable;

/**
 * 实体接口;
 * 
 * @author crazyjohn
 *
 */
public interface IEntity<ID extends Serializable> {
	public ID getId();
}
