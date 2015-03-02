package com.stone.game.msg.handler;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;

import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.handler.IMessageHandlerWithType;
import com.stone.game.player.login.PlayerLoginHandler;
import com.stone.proto.MessageTypes.MessageType;

/**
 * Message handler registry;
 * 
 * @author crazyjohn;
 */
public class MessageHandlerRegistry {
	private static Map<MessageType, IMessageHandlerWithType<?>> handlers = new HashMap<MessageType, IMessageHandlerWithType<?>>();
	protected static ActorRef dbMaster;

	public static void init(ActorRef dbMaster) {
		MessageHandlerRegistry.dbMaster = dbMaster;
		// register handler
		handlers.put(MessageType.CG_PLAYER_LOGIN, new PlayerLoginHandler(dbMaster));
	}

	@SuppressWarnings("unchecked")
	public static void handle(IProtobufMessage protobufMessage) throws MessageParseException {
		@SuppressWarnings("rawtypes")
		IMessageHandlerWithType handler = handlers.get(MessageType.valueOf(protobufMessage.getType()));
		if (handler == null) {
			// TODO: prompt
			return;
		}
		handler.execute(protobufMessage);
	}

}
