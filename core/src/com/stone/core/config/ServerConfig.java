package com.stone.core.config;

import com.stone.proto.Servers.ServerInfo;
import com.stone.proto.Servers.ServerType;

/**
 * The base server config;
 * 
 * @author crazyjohn
 *
 */
public class ServerConfig implements IConfig {
	private String bindIp;
	private int port;
	private ServerInfo.Builder serverInfo = ServerInfo.newBuilder();

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

	public ServerInfo.Builder getServerInfo() {
		return serverInfo.setType(ServerType.GAME);
	}

}
