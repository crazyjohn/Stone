package com.stone.core.node.info;

import com.stone.core.session.ISession;
import com.stone.proto.Servers.ServerInfo;

public class CommonServerInfo {
	private final ISession session;
	private final ServerInfo serverInfo;

	public CommonServerInfo(ISession session, ServerInfo serverInfo) {
		this.session = session;
		this.serverInfo = serverInfo;
	}

	public ISession getSession() {
		return session;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}
}
