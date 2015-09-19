package com.stone.core.msg.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.ProtobufMessage;

public class GCMessage extends BaseForwardMessage {

	public GCMessage(int type) {
		super(type);
	}

	public GCMessage(int messageType, Builder builder, long playerId, int sceneId) {
		super(messageType, builder, playerId, sceneId, "");
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
		// write builder
		if (this.builder == null) {
			return true;
		}
		this.buf.put(builder.build().toByteArray());
		return true;
	}

	@Override
	protected boolean readBody() throws Exception {
		// read playerInfo
		this.playerId = readLong();
		this.sceneId = readInt();
		// read builder
		int bodyLength = this.messageLength - IMessage.HEADER_SIZE - PLAYER_ID_SIZE - SCENE_ID_SIZE;
		if (bodyLength == 0) {
			return true;
		}
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
