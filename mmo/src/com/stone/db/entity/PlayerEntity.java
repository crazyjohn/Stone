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
	@Column(name = "puid")
	private String puid;

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

}
