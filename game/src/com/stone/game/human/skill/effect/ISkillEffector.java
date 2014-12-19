package com.stone.game.human.skill.effect;

import java.util.List;

import com.stone.game.human.battle.BattleField;
import com.stone.game.human.battle.BattleRole;

/**
 * 技能效果解释接口;
 * <p>
 * 1. 根据策划需求去实现原子的技能效果<br>
 * 2. 每个效果都有自己需要的参数@param params, 然后根据指定参数的语义进行解释<br>
 * 3. 技能由多个效果的一个效果链组成(但是coder需要根据情况指定一些规则:比如最多两个效果, 第一个效果规定是哪种类型等, 否则会不可靠)<br>
 * @author crazyjohn
 *
 */
public interface ISkillEffector {
	/**
	 * 技能效果解释接口;
	 * 
	 * @param field
	 * @param battleRole
	 * @param actions
	 * @param params
	 *            效果参数值, 根据不同类型进行自解释;
	 * @return
	 */
	IEffectResult effect(BattleField field, BattleRole battleRole,
			List<IAction> actions, int[] params);
}
