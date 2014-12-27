package com.stone.db.entity;

import com.stone.core.entity.IEntity;

/**
 * 技能实体;
 * 
 * @author crazyjohn
 *
 */
public class SkillEntity implements IEntity<Integer> {
	private int id;
	
	public void setId(int id) {
		this.id = id;
	}

	private int isEquiped;

	@Override
	public Integer getId() {
		return id;
	}

	public int getIsEquiped() {
		return isEquiped;
	}

}
