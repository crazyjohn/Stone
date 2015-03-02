package com.stone.game.msg.handler;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;

import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.player.Player;
import com.stone.game.player.login.PlayerCreateRoleHandler;
import com.stone.game.player.login.PlayerGetRoleListHandler;
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
		handlers.put(MessageType.CG_GET_ROLE_LIST, new PlayerGetRoleListHandler());
		handlers.put(MessageType.CG_CREATE_ROLE, new PlayerCreateRoleHandler());
	}

	@SuppressWarnings("unchecked")
	public static void handle(IProtobufMessage protobufMessage, Player player) throws MessageParseException {
		@SuppressWarnings("rawtypes")
		IMessageHandlerWithType handler = handlers.get(MessageType.valueOf(protobufMessage.getType()));
		if (handler == null) {
			// TODO: prompt
			return;
		}
		handler.execute(protobufMessage, player);
	}

}
