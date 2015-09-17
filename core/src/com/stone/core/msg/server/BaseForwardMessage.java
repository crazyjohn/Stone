package com.stone.core.msg.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.BaseActorMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.MessageParseException;

public class BaseForwardMessage extends BaseActorMessage {
	private static final int PLAYER_ID_SIZE = 8;
	private static final int SCENE_ID_SIZE = 4;
	private static final int STRING_LENGTH = 2;
	private long playerId;
	private int sceneId;
	private String clientIp;
	private Builder builder;
	private volatile boolean alreadyParsed = false;
	private byte[] builderDatas;

	public BaseForwardMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		this.type = (short) messageType;
		this.playerId = playerId;
		this.sceneId = sceneId;
		this.clientIp = clientIp;
		this.builder = builder;
	}

	public BaseForwardMessage(int type) {
		this(type, null, -1, -1, "");
	}

	public byte[] getBuilderDatas() {
		return builderDatas;
	}

	public void setBuilderDatas(byte[] builderDatas) {
		this.builderDatas = builderDatas;
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

	@SuppressWarnings("unchecked")
	public <B extends Builder> B getBuilder(Builder builder) throws Exception {
		if (this.builder == null) {
			this.parseBuilder(builder);
		}
		return (B) this.builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	@Override
	protected boolean readBody() throws Exception {
		// read playerInfo
		this.playerId = readLong();
		this.sceneId = readInt();
		this.clientIp = readString();
		// read builder
		int bodyLength = this.messageLength - IMessage.HEADER_SIZE - PLAYER_ID_SIZE - SCENE_ID_SIZE - this.clientIp.getBytes("UTF-8").length
				- STRING_LENGTH;
		byte[] bodys = new byte[bodyLength];
		this.buf.get(bodys);
		this.builderDatas = bodys;
		if (builder == null) {
			return true;
		}
		try {
			this.builder = builder.mergeFrom(bodys);
			this.alreadyParsed = true;
		} catch (InvalidProtocolBufferException e) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean writeBody() {
		// write playerInfo
		this.writeLong(this.playerId);
		this.writeInt(this.sceneId);
		this.writeString(this.clientIp);
		// write builder
		if (builder == null) {
			return true;
		}
		this.buf.put(builder.build().toByteArray());
		return true;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public void execute() throws MessageParseException {
		// TODO Auto-generated method stub

	}

}
