package com.stone.agent.msg.internal;

import com.stone.core.msg.ProtobufMessage;

public class SelectRoleFromGame {
	private final int sceneId;
	private final ProtobufMessage msg;

	public SelectRoleFromGame(int sceneId, ProtobufMessage msg) {
		this.sceneId = sceneId;
		this.msg = msg;
	}

	public int getSceneId() {
		return sceneId;
	}

	public ProtobufMessage getProtobufMessage() {
		return msg;
	}

}
