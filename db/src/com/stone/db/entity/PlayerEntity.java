package com.stone.db.entity;

import com.stone.core.entity.IEntity;

public class PlayerEntity implements IEntity<Long> {
	private long id;
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

}
