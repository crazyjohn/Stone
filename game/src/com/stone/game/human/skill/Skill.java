package com.stone.game.human.skill;

import java.util.ArrayList;
import java.util.List;

import com.stone.core.game.IGameObject;
import com.stone.db.entity.SkillEntity;
import com.stone.game.human.battle.BattleField;
import com.stone.game.human.battle.BattleRole;
import com.stone.game.human.skill.effect.ISkillEffector;

/**
 * 技能业务对象;
 * 
 * @author crazyjohn
 *
 */
public class Skill implements IGameObject<SkillEntity, Skill>, ISkill {
	private boolean isEquiped;
	private int skillId;
	/** 效果列表 */
	private List<ISkillEffector> effectors = new ArrayList<ISkillEffector>();

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

	@Override
	public List<ISkillEffector> getEffectors() {
		return effectors;
	}

	@Override
	public boolean canUseSkill() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useSkill(BattleField field, BattleRole battleRole) {
		// TODO 使用解释器链去解释技能效果;
		return false;
	}

}
