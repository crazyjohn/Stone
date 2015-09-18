package com.stone.core.msg.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.BaseActorMessage;
import com.stone.core.msg.MessageParseException;

public abstract class BaseForwardMessage extends BaseActorMessage {
	protected static final int PLAYER_ID_SIZE = 8;
	protected static final int SCENE_ID_SIZE = 4;
	protected static final int STRING_LENGTH = 2;
	protected long playerId;
	protected int sceneId;
	protected String clientIp;
	protected Builder builder;
	protected volatile boolean alreadyParsed = false;
	protected byte[] builderDatas;

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
