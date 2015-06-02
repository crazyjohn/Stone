package com.stone.core.config;

/**
 * The base server config;
 * 
 * @author crazyjohn
 *
 */
public class ServerConfig implements IConfig {
	private String bindIp;
	private int port;
	/** is debug */
	private boolean isDebug;

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
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	public boolean getIsDebug() {
		return isDebug;
	}

	public void setIsDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

}
