package com.stone.core.msg;

import com.stone.core.codec.IMessageFactory;

/**
 * protobuf消息工厂;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessageFactory implements IMessageFactory {

	@Override
	public IProtobufMessage createMessage(int type) {
		ProtobufMessage message = new ProtobufMessage(type);
		return message;
	}
}
