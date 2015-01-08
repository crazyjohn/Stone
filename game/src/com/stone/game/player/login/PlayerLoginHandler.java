package com.stone.game.player.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.annotation.MessageHandler;
import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.handler.BaseProtobufMessageHandler;
import com.stone.proto.Auths.Login;
import com.stone.proto.MessageTypes.MessageType;

@MessageHandler
public class PlayerLoginHandler extends BaseProtobufMessageHandler {
	private Logger logger = LoggerFactory.getLogger(PlayerLoginHandler.class);

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN_VALUE;
	}

	@Override
	public void execute(IProtobufMessage msg) throws MessageParseException {
		Login.Builder login = msg.parseBuilder(Login.newBuilder());
		logger.info(String.format("Player login, userName: %s",
				login.getUserName()));
	}

}
