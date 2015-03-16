package com.stone.db.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.stone.core.entity.IEntity;

/**
 * The human db entity;
 * 
 * @author crazyjohn
 *
 */
@Entity
@Table(name = "human")
public class HumanEntity implements IEntity {
	@Id
	@Column(name = "guid")
	private long guid;
	@Column(name = "playerId")
	private long playerId;
	@Column(name = "name")
	private String name;
	@Column(name = "level")
	private int level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

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

	/**
	 * Get item entities;
	 * 
	 * @return
	 */
	public List<HumanItemEntity> getItemEntities() {
		// TODO Auto-generated method stub
		return null;
	}

}
