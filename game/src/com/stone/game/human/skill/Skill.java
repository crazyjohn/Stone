package com.stone.game.human.skill;

import com.stone.core.game.IGameObject;
import com.stone.db.entity.SkillEntity;

/**
 * 技能业务对象;
 * 
 * @author crazyjohn
 *
 */
public class Skill implements IGameObject<SkillEntity, Skill>{
	private boolean isEquiped;
	private int skillId;

	public boolean isEquiped() {
		return isEquiped;
	}

	public void equipMe() {
		isEquiped = true;
	}

	@Override
	public SkillEntity toEntity() {
		SkillEntity skillEntity = new SkillEntity();
		return skillEntity;
	}

	@Override
	public Skill fromEntity(SkillEntity entity) {
		setSkillId(entity.getId());
		isEquiped = (entity.getIsEquiped() == 1);
		return this;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

}
