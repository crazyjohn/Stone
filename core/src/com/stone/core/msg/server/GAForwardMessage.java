package com.stone.core.msg.server;

import com.google.protobuf.Message.Builder;

public class GAForwardMessage extends AGForwardMessage {

	public GAForwardMessage(int messageType, Builder builder, long playerId, int sceneId, String clientIp) {
		super(messageType, builder, playerId, sceneId, clientIp);
	}

}
