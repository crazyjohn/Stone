package com.stone.game.player.login;

import com.stone.core.msg.MessageParseException;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.Player;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerCreateRoleHandler implements IMessageHandlerWithType<ProtobufMessage> {

	@Override
	public void execute(ProtobufMessage msg, Player player) throws MessageParseException {
		// TODO create a role
		System.out.println("Want create a role.");
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_CREATE_ROLE_VALUE;
	}

}
