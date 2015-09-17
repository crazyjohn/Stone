package com.stone.core.msg.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.BaseCAMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.MessageParseException;

public class AGForwardMessage extends BaseCAMessage {

	private static final int PLAYER_ID_SIZE = 8;
	private static final int SCENE_ID_SIZE = 4;
	private long playerId;
	private int sceneId;
	private String clientIp;
	private Builder builder;

	public AGForwardMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		this.type = messageType;
		this.playerId = playerId;
		this.sceneId = sceneId;
		this.clientIp = clientIp;
		this.builder = builder;
	}

	public AGForwardMessage(int type) {
		this(type, null, -1, -1, "");
	}

	@SuppressWarnings("unchecked")
	public <B extends Builder> B getBuilder() {
		return (B) builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	@Override
	protected boolean readBody() {
		// read playerInfo
		this.playerId = readLong();
		this.sceneId = readInt();
		this.clientIp = readString();
		// read builder
		int bodyLength = this.messageLength - IMessage.HEADER_SIZE - PLAYER_ID_SIZE - SCENE_ID_SIZE - this.clientIp.getBytes().length;
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
