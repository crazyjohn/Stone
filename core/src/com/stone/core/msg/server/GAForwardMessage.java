package com.stone.core.msg.server;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.ProtobufMessage;

public class GAForwardMessage extends BaseForwardMessage {

	public GAForwardMessage(int type) {
		super(type);
	}

	public GAForwardMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		super(messageType, builder, playerId, sceneId, clientIp);
	}

	public ProtobufMessage build() {
		ProtobufMessage msg = new ProtobufMessage(this.getType());
		return msg;
	}

}
