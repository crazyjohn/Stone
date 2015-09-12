package com.stone.core.config;

import com.stone.proto.Servers.ServerType;

public class MasterAddress {
	private final String masterName;
	private final String host;
	private final int port;
	private final ServerType serverType;

	public MasterAddress(String masterName, String host, int port, ServerType serverType) {
		this.masterName = masterName;
		this.host = host;
		this.port = port;
		this.serverType = serverType;
	}

	public String getHost() {
		return host;
	}

	public String getMasterName() {
		return masterName;
	}

	public int getPort() {
		return port;
	}

	public ServerType getServerType() {
		return serverType;
	}
}
