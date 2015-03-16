package com.stone.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.stone.core.entity.IEntity;

@Entity
@Table(name = "player")
public class PlayerEntity implements IEntity {
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "userName")
	private String userName;
	@Column(name = "password")
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
