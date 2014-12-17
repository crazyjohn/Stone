package com.stone.core.entity;

import java.io.Serializable;

/**
 * 数据库实体接口;
 * 
 * @author crazyjohn
 *
 * @param <ID>
 */
public interface IEntity<ID extends Serializable> {
	public ID getId();
}
