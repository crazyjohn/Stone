package com.stone.game.entity;

import com.stone.core.entity.IEntity;

public class GameEntity implements IEntity<Long> {
	private long id;
	@Override
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
