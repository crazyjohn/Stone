package com.stone.agent.player;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.ProtobufMessage;

public class AgentPlayer {
	private final IoSession session;
	private long playerId;

	public AgentPlayer(IoSession session) {
		this.session = session;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public void sendMessage(int messageType, Builder builder) {
		ProtobufMessage message = new ProtobufMessage(messageType);
		message.setBuilder(builder);
		this.session.write(message);
	}

	public String getClientIp() {
		return session.getRemoteAddress().toString();
	}

}
