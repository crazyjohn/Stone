package com.stone.game.player.login;

import com.stone.core.msg.MessageType;
import com.stone.game.handler.IProtobufHandler;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.Login.Builder;

public class PlayerLoginHandler implements IProtobufHandler<Login.Builder> {

	@Override
	public void execute(Builder message) {
		// TODO Auto-generated method stub

	}

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN;
	}

}
