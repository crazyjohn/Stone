package com.stone.game;

import java.util.Properties;

import com.stone.core.config.ServerConfig;

/**
 * The game server config;
 * 
 * @author crazyjohn
 *
 */
public class GameServerConfig extends ServerConfig {
	/** the dbSerive type */
	private String dbServiceType = "hibernate";
	private Properties dataServiceProperties = new Properties();
	private String dbConfigName;
	private String agentHost;
	private int agentPort;
	private String worldHost;
	private int worldPort;

	public String getWorldHost() {
		return worldHost;
	}

	public void setWorldHost(String worldHost) {
		this.worldHost = worldHost;
	}

	public int getWorldPort() {
		return worldPort;
	}

	public void setWorldPort(int worldPort) {
		this.worldPort = worldPort;
	}

	public String getAgentHost() {
		return agentHost;
	}

	public void setAgentHost(String agentHost) {
		this.agentHost = agentHost;
	}

	public int getAgentPort() {
		return agentPort;
	}

	public void setAgentPort(int agentPort) {
		this.agentPort = agentPort;
	}

	public Properties getDataServiceProperties() {
		return dataServiceProperties;
	}

	public String getDbServiceType() {
		return dbServiceType;
	}

	public String getDbConfigName() {
		return dbConfigName;
	}

	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}

	public void setDbServiceType(String dbServiceType) {
		this.dbServiceType = dbServiceType;
	}

	public void setDataServiceProperties(Properties dataServiceProperties) {
		this.dataServiceProperties = dataServiceProperties;
	}

}
