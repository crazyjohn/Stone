package com.stone.game.human.skill.effect.buff;

import java.util.List;

import com.stone.game.human.battle.BattleField;
import com.stone.game.human.battle.BattleRole;
import com.stone.game.human.skill.effect.IAction;
import com.stone.game.human.skill.effect.IEffectResult;
import com.stone.game.human.skill.effect.ISkillEffector;

/**
 * 一定几率给对方上buff的效果;<br>
 * params[0] = 概率;<br>
 * params[1] = buff类型;<br>
 * params[2] = 回合数;<br>
 * 
 * @author crazyjohn
 *
 */
public class GiveBuffRateEffector implements ISkillEffector {

	@Override
	public IEffectResult effect(BattleField field, BattleRole battleRole,
			List<IAction> actions, int[] params) {
		// TODO Auto-generated method stub
		return null;
	}

}
