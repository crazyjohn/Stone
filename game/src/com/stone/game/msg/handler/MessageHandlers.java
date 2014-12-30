package com.stone.game.msg.handler;

import java.util.HashMap;
import java.util.Map;

import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.MessageType;
import com.stone.core.msg.handler.IMessageHandlerWithType;

/**
 * 处理器工厂;
 * 
 * @author crazyjohn
 *
 */
public class MessageHandlers {
	private static Map<Short, IMessageHandlerWithType<?>> handlers = new HashMap<Short, IMessageHandlerWithType<?>>();
	
	static {
		handlers.put(MessageType.CG_PLAYER_LOGIN, null);
	}

	@SuppressWarnings("unchecked")
	public static void handle(IProtobufMessage protobufMessage)
			throws MessageParseException {
		@SuppressWarnings("rawtypes")
		IMessageHandlerWithType handler = handlers.get(protobufMessage
				.getType());
		if (handler == null) {
			// TODO: prompt
			return;
		}
		handler.execute(protobufMessage);
	}

}
