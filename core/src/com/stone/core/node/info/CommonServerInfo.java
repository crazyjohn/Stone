package com.stone.core.node.info;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;
import com.stone.core.session.ISession;
import com.stone.proto.Servers.ServerInfo;

public class CommonServerInfo {
	private final ISession session;
	private final ServerInfo serverInfo;

	public CommonServerInfo(IoSession ioSession, String serverName) {
		this.session = new BaseSession(ioSession);
		this.serverInfo = ServerInfo.newBuilder().setName(serverName).build();
	}

	public ISession getSession() {
		return session;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}
}
