package com.stone.game.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.IProtobufMessage;
import com.stone.core.msg.MessageParseException;

/**
 * Protobuf message;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessage extends BaseCGMessage implements IProtobufMessage {
	protected Builder builder;

	public ProtobufMessage(short messageType) {
		this.type = messageType;
	}

	public ProtobufMessage(int messageType) {
		this.type = (short) messageType;
	}

	@Override
	public void execute() throws MessageParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean readBody() {
		int bodyLength = this.messageLength - IMessage.HEADER_SIZE;
		byte[] bodys = new byte[bodyLength];
		this.buf.get(bodys);
		if (builder == null) {
			return true;
		}
		try {
			this.builder = builder.mergeFrom(bodys);
		} catch (InvalidProtocolBufferException e) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean writeBody() {
		if (builder == null) {
			return true;
		}
		this.buf.put(builder.build().toByteArray());
		return true;
	}

	@Override
	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B extends Builder> B parseBuilder(B newBuilder) throws MessageParseException {
		this.builder = newBuilder;
		this.read();
		return (B) builder;
	}

}
