package com.stone.game.player.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.annotation.ProtobufHandler;
import com.stone.core.msg.MessageType;
import com.stone.game.handler.IProtobufHandler;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.Login.Builder;

/**
 * 登录处理;
 * 
 * @author crazyjohn
 * 
 */
@ProtobufHandler
public class PlayerLoginHandler implements IProtobufHandler<Login.Builder> {
	private Logger logger = LoggerFactory.getLogger(PlayerLoginHandler.class);
	
	@Override
	public void execute(Builder message) {
		String userName = message.getUserName();
		logger.info(String.format("Player login, name: %s", userName));
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN;
	}

}
