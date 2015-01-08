package com.stone.game.msg.handler;

import java.util.HashMap;
import java.util.Map;

import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.handler.IMessageHandlerWithType;
import com.stone.game.player.login.PlayerLoginHandler;
import com.stone.proto.MessageTypes.MessageType;

/**
 * 处理器工厂;
 * 
 * @author crazyjohn
 *
 */
public class MessageHandlers {
	private static Map<MessageType, IMessageHandlerWithType<?>> handlers = new HashMap<MessageType, IMessageHandlerWithType<?>>();

	static {
		handlers.put(MessageType.CG_PLAYER_LOGIN, new PlayerLoginHandler());
	}

	@SuppressWarnings("unchecked")
	public static void handle(IProtobufMessage protobufMessage)
			throws MessageParseException {
		@SuppressWarnings("rawtypes")
		IMessageHandlerWithType handler = handlers.get(MessageType
				.valueOf(protobufMessage.getType()));
		if (handler == null) {
			// TODO: prompt
			return;
		}
		handler.execute(protobufMessage);
	}

}
