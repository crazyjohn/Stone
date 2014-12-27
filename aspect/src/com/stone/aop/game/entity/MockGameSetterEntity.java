package com.stone.aop.game.entity;

import com.stone.core.entity.IEntity;

public class MockGameSetterEntity implements IEntity<Long> {
	private long id;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
