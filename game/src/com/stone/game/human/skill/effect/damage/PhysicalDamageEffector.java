package com.stone.game.human.skill.effect.damage;

import java.util.List;

import com.stone.game.human.battle.BattleField;
import com.stone.game.human.battle.BattleRole;
import com.stone.game.human.skill.effect.IAction;
import com.stone.game.human.skill.effect.IEffectResult;
import com.stone.game.human.skill.effect.ISkillEffector;

/**
 * 直接物理伤害效果;
 * params[0] = 目标类型(1-全体, 2-单体);<br>
 * params[1] = 伤害值;<br>
 * @author crazyjohn
 *
 */
public class PhysicalDamageEffector implements ISkillEffector {

	@Override
	public IEffectResult effect(BattleField field, BattleRole battleRole,
			List<IAction> actions, int[] params) {
		// TODO Auto-generated method stub
		return null;
	}

}
