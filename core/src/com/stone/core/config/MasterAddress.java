package com.stone.core.config;

public class MasterAddress {
	private final String masterName;
	private final String host;
	private final int port;

	public MasterAddress(String masterName, String host, int port) {
		this.masterName = masterName;
		this.host = host;
		this.port = port;
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
}
