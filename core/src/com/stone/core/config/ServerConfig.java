package com.stone.core.config;

/**
 * 服务器基础配置;
 * 
 * @author crazyjohn
 *
 */
public class ServerConfig implements IConfig {
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
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
