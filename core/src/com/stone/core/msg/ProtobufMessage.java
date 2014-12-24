package com.stone.core.msg;

import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 基础的protobuf消息;
 * 
 * @author crazyjohn
 *
 * @param <B>
 */
public class ProtobufMessage<B extends Builder<B>> extends BaseMessage
		implements IProtobufMessage<B> {
	protected B builder;

	@Override
	public B getBuilder() {
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
