package com.stone.agent;

import com.stone.core.config.ServerConfig;

public class AgentServerConfig extends ServerConfig {
	private int internalPort;

	public int getInternalPort() {
		return internalPort;
	}

	public void setInternalPort(int internalPort) {
		this.internalPort = internalPort;
	}
}
