package com.stone.game.player.login;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.Player;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerGetRoleListHandler implements IMessageHandlerWithType<ProtobufMessage> {
	private final ActorRef dbMaster;

	public PlayerGetRoleListHandler(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
	}

	@Override
	public void execute(ProtobufMessage msg, Player player) throws MessageParseException {
		// get role list
		InternalGetRoleList getRoleList = new InternalGetRoleList(player.getPlayerId());
		dbMaster.tell(getRoleList, msg.getPlayerActor());
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ROLE_LIST_VALUE;
	}

}
