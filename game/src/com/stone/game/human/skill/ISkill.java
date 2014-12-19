package com.stone.game.human.skill;

import java.util.List;

import com.stone.game.human.battle.BattleField;
import com.stone.game.human.battle.BattleRole;
import com.stone.game.human.skill.effect.ISkillEffector;

/**
 * 技能接口;
 * 
 * @author crazyjohn
 *
 */
public interface ISkill {
	/**
	 * 获取技能ID;
	 * 
	 * @return
	 */
	public int getSkillId();

	/**
	 * 获取技能解释器列表;
	 * 
	 * @return
	 */
	public List<ISkillEffector> getEffectors();

	/**
	 * 是否可以使用此技能;
	 * 
	 * @return
	 */
	public boolean canUseSkill();

	/**
	 * 使用技能;
	 * 
	 * @param field
	 * @param battleRole
	 * @return
	 */
	public boolean useSkill(BattleField field, BattleRole battleRole);
}
