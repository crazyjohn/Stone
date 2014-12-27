package com.stone.aop.db;

import com.stone.aop.annotation.NotifyUpdate;
import com.stone.core.entity.IEntity;

public class MockAnnotationEntity implements IEntity<Long> {
	private long id;

	@Override
	public Long getId() {
		return id;
	}

	@NotifyUpdate(async = true)
	public void setId(long id) {
		this.id = id;
	}

}
