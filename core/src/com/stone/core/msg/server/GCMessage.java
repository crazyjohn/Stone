package com.stone.core.msg.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.ProtobufMessage;

public class GCMessage extends BaseForwardMessage {

	public GCMessage(int type) {
		super(type);
	}

	public GCMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		super(messageType, builder, playerId, sceneId, clientIp);
	}

	public ProtobufMessage build() {
		ProtobufMessage msg = new ProtobufMessage(this.getType());
		return msg;
	}

	@Override
	protected boolean writeBody() {
		// write playerInfo
		this.writeLong(this.playerId);
		this.writeInt(this.sceneId);
		this.writeString(this.clientIp);
		// write builder
		this.buf.put(builder.build().toByteArray());
		return true;
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
}
