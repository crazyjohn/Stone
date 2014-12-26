package com.stone.game.msg;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message.Builder;
import com.stone.core.codec.IMessageFactory;
import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageType;
import com.stone.proto.Auths.Login;

/**
 * protobuf消息工厂;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessageFactory implements IMessageFactory {
	private Map<Short, Builder> builders = new HashMap<Short, Builder>();

	{
		builders.put(MessageType.CG_PLAYER_LOGIN, Login.newBuilder());
	}

	@Override
	public IProtobufMessage createMessage(short type) {
		Builder builder = getBuilderByType(type);
		if (builder == null) {
			return null;
		}
		ProtobufMessage message = new ProtobufMessage(type, builder);
		return message;
	}

	private Builder getBuilderByType(short type) {
		return builders.get(type);
	}

}
