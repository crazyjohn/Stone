package com.stone.game.player.login;

import com.stone.core.msg.MessageParseException;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.Player;
import com.stone.proto.Auths.GetRoleList;
import com.stone.proto.Auths.Role;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerGetRoleListHandler implements IMessageHandlerWithType<ProtobufMessage> {

	@Override
	public void execute(ProtobufMessage msg, Player player) throws MessageParseException {
		// get role list
		GetRoleList.Builder roleList = GetRoleList.newBuilder();
		Role.Builder role = Role.newBuilder().setRoleId(888L).setName("crazyjohn");
		roleList.addRoleList(role);
		player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, roleList);
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ROLE_LIST_VALUE;
	}

}
