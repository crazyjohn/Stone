package com.stone.game.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.game.msg.handler.MessageHandlerRegistry;

/**
 * 基础的protobuf消息;
 * 
 * @author crazyjohn
 *
 * @param <B>
 */
public class ProtobufMessage extends BaseCGMessage implements IProtobufMessage {
	protected Builder builder;

	public ProtobufMessage(short messageType) {
		this.type = messageType;
	}

	@Override
	public void execute() throws MessageParseException {
		MessageHandlerRegistry.handle(this);
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

	@Override
	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B extends Builder> B parseBuilder(B newBuilder)
			throws MessageParseException {
		this.builder = newBuilder;
		this.read();
		return (B) builder;
	}

}
