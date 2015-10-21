package com.stone.agent.player;

import java.net.InetSocketAddress;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.GCMessage;
import com.stone.db.entity.PlayerEntity;

public class AgentPlayer {
	private final IoSession session;
	private PlayerEntity entity;
	private String clientIp;

	public AgentPlayer(IoSession session) {
		this.session = session;
		this.clientIp = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
	}

	public long getPlayerId() {
		return entity.getId();
	}

	public void sendMessage(int messageType, Builder builder) {
		ProtobufMessage message = new ProtobufMessage(messageType);
		message.setBuilder(builder);
		this.session.write(message);
	}

	public void sendMessage(GCMessage message) {
		// build protobuf message
		ProtobufMessage protoMessage = message.build();
		protoMessage.setBuilderDatas(message.getBuilderDatas());
		this.session.write(protoMessage);
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setEntity(PlayerEntity playerEntity) {
		this.entity = playerEntity;
	}

	public String getPuid() {
		return this.entity.getPuid();
	}

}
