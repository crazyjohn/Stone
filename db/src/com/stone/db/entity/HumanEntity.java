package com.stone.db.entity;

import com.stone.core.entity.IEntity;

/**
 * 玩家数据库实体;
 * 
 * @author crazyjohn
 *
 */
public class HumanEntity implements IEntity<Long> {
	private long guid;
	@Override
	public Long getId() {
		return guid;
	}
	public long getGuid() {
		return guid;
	}
	public void setGuid(long guid) {
		this.guid = guid;
	}

}
