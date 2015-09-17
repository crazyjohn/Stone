package com.stone.core.msg.server;

import com.google.protobuf.Message.Builder;

public class AGForwardMessage extends BaseForwardMessage {

	public AGForwardMessage(int type) {
		super(type);
	}

	public AGForwardMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		super(messageType, builder, playerId, sceneId, clientIp);
	}

}
