package com.stone.game.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.IProtobufMessage;

/**
 * 基础的protobuf消息;
 * 
 * @author crazyjohn
 *
 * @param <B>
 */
public class ProtobufMessage extends BaseCGMessage implements IProtobufMessage {
	protected Builder builder;

	public ProtobufMessage(short messageType, Builder protoBuilder) {
		this.type = messageType;
		this.builder = protoBuilder;
	}

	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean readBody() {
		int bodyLength = this.messageLength - IMessage.HEADER_SIZE;
		byte[] bodys = new byte[bodyLength];
		this.buf.get(bodys);
		try {
			this.builder = builder.mergeFrom(bodys);
		} catch (InvalidProtocolBufferException e) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean writeBody() {
		this.buf.put(builder.build().toByteArray());
		return true;
	}

}
