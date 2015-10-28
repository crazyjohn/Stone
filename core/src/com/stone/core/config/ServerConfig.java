package com.stone.core.config;


/**
 * The base server config;
 * 
 * @author crazyjohn
 *
 */
public class ServerConfig implements IConfig {
	private String name;
	private String bindIp;
	private int port;

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void validate() {
		// TODO do nothing ??

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
