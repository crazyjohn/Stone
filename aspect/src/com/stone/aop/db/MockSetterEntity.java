package com.stone.aop.db;

import com.stone.core.entity.IEntity;

public class MockSetterEntity implements IEntity<Long> {
	private long id;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
