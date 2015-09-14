package com.stone.agent.player;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.ProtobufMessage;

public class AgentPlayer {
	private final IoSession session;

	public AgentPlayer(IoSession session) {
		this.session = session;
	}

	public long getPlayerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setPlayerId(long id) {
		// TODO Auto-generated method stub

	}

	public void sendMessage(int messageType, Builder builder) {
		ProtobufMessage message = new ProtobufMessage(messageType);
		message.setBuilder(builder);
		this.session.write(message);
	}

}
