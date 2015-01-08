package com.stone.game.human.skill.handler;

import com.stone.core.annotation.MessageHandler;
import com.stone.core.msg.handler.IMessageHandlerWithType;
import com.stone.game.human.Human;
import com.stone.game.human.skill.msg.CGEquipSkillMessage;
import com.stone.proto.MessageTypes.MessageType;

/**
 * 装备技能处理器;
 * 
 * @author crazyjohn
 *
 */
@MessageHandler
public class HumanEquipSkillHandler implements
		IMessageHandlerWithType<CGEquipSkillMessage> {

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN_VALUE;
	}

	@Override
	public void execute(CGEquipSkillMessage msg) {
		// 装备技能;
		Human human = msg.getPlayer().getHuman();
		human.getSkillManager().equipSkill(msg.getSkillId());
	}

}
