package com.stone.game.human.skill.msg;

import com.stone.game.msg.BaseCGMessage;

/**
 * 客户端请求装备技能的消息;
 * 
 * @author crazyjohn
 *
 */
public class CGEquipSkillMessage extends BaseCGMessage {
	private int skillId;

	public int getSkillId() {
		return skillId;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean readBody() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean writeBody() {
		// TODO Auto-generated method stub
		return false;
	}

}
