package com.stone.game.human.skill;

import java.util.HashMap;
import java.util.Map;

import com.stone.db.entity.HumanEntity;
import com.stone.game.human.Human;
import com.stone.game.human.IHumanPersistenceManager;

/**
 * 玩家技能管理器;
 * 
 * @author crazyjohn
 *
 */
public class HumanSkillManager implements IHumanPersistenceManager {
	private Map<Integer, Skill> skills = new HashMap<Integer, Skill>();
	protected Human owner;
	@Override
	public void onLoad(HumanEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPersistence(HumanEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Human getOwner() {
		return owner;
	}

	/**
	 * 装备技能;
	 */
	public void equipSkill(int skillId) {
		Skill skill = skills.get(skillId);
		if (skill == null) {
			return;
		}
		if (!skill.isEquiped()) {
			return;
		}
		skill.equipMe();
	}

}
