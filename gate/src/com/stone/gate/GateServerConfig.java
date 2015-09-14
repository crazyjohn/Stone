package com.stone.gate;

import com.stone.core.config.ServerConfig;

public class GateServerConfig extends ServerConfig {
	private int internalPort;

	public int getInternalPort() {
		return internalPort;
	}

	public void setInternalPort(int internalPort) {
		this.internalPort = internalPort;
	}
}
