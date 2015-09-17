package com.stone.core.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.googlecode.protobuf.format.JsonFormat;
import com.stone.core.session.BaseActorSession;
import com.stone.proto.MessageTypes.MessageType;

/**
 * Protobuf message;
 * 
 * @author crazyjohn
 *
 */
public class ProtobufMessage extends BaseSessionMessage<BaseActorSession> implements IProtobufMessage {
	protected Builder builder;
	private volatile boolean alreadyParsed = false;
	private byte[] builderDatas;

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
		this.builderDatas = bodys;
		if (builder == null) {
			return true;
		}

		return true;
	}

	public byte[] getBuilderDatas() {
		return builderDatas;
	}

	public void setBuilderDatas(byte[] builderDatas) {
		this.builderDatas = builderDatas;
	}

	@Override
	protected boolean writeBody() {
		if (builder == null) {
			if (this.builderDatas != null) {
				writeBuilderBytes();
			}
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
	protected <B extends Builder> B parseBuilder(Builder builder) throws InvalidProtocolBufferException {
		if (this.alreadyParsed) {
			throw new IllegalStateException(String.format("This %s builder already parsed.", builder.getClass().getSimpleName()));
		}
		this.builder = builder;
		this.builder = builder.mergeFrom(this.builderDatas);
		this.alreadyParsed = true;
		return (B) this.builder;
	}

	@Override
	public String toString() {
		return "[type: " + MessageType.valueOf(this.type).toString() + ", builder: "
				+ (this.builder == null ? "null]" : JsonFormat.printToString(this.builder.clone().build()) + "]");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B extends Builder> B getBuilder(Builder builder) throws InvalidProtocolBufferException {
		if (this.builder == null) {
			this.parseBuilder(builder);
		}
		return (B) this.builder;
	}

	protected void writeBuilderBytes() {
		this.writeBytes(this.builderDatas);
	}

}
