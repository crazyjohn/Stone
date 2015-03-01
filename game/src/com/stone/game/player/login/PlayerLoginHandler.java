package com.stone.game.player.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.handler.IMessageHandlerWithType;
import com.stone.game.msg.ProtobufMessage;
import com.stone.proto.Auths.Login;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerLoginHandler implements IMessageHandlerWithType<ProtobufMessage> {
	private Logger logger = LoggerFactory.getLogger(PlayerLoginHandler.class);

	@Override
	public void execute(ProtobufMessage msg) throws MessageParseException {
		// get actor ref
		ActorRef playerActor = msg.getPlayerActor();
		final Login.Builder login = msg.parseBuilder(Login.newBuilder());
		playerActor.tell(login, ActorRef.noSender());
		logger.info(String.format("Player login, userName: %s", login.getUserName()));
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN_VALUE;
	}

}
