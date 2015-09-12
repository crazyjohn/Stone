package com.stone.core.node.info;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.session.BaseSession;
import com.stone.core.session.ISession;
import com.stone.proto.Servers.ServerInfo;
import com.stone.proto.Servers.ServerType;

public class CommonServerInfo {
	private final ISession session;
	private final ServerInfo serverInfo;

	public CommonServerInfo(IoSession ioSession, String serverName, ServerType serverType) {
		this.session = new BaseSession(ioSession);
		this.serverInfo = ServerInfo.newBuilder().setName(serverName).setType(serverType).build();
	}

	public ISession getSession() {
		return session;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void sendMessage(int messageType, Builder builder) {
		ProtobufMessage message = new ProtobufMessage(messageType);
		message.setBuilder(builder);
		this.session.writeMessage(message);
	}
}
