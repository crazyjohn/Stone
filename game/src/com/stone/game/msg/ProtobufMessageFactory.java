package com.stone.game.msg;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.msg.IProtobufMessage;

/**
 * protobuf消息工厂;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessageFactory implements IMessageFactory {

	@Override
	public IProtobufMessage createMessage(short type) {
		ProtobufMessage message = new ProtobufMessage(type);
		return message;
	}
}
