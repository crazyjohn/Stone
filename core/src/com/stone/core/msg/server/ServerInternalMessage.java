package com.stone.core.msg.server;

import com.stone.core.msg.ProtobufMessage;

public class ServerInternalMessage {
	private final ProtobufMessage msg;

	public ServerInternalMessage(ProtobufMessage msg) {
		this.msg = msg;
	}

	public ProtobufMessage getRealMessage() {
		return msg;
	}
}
