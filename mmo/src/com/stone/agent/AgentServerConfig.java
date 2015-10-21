package com.stone.agent;

import java.util.Properties;

import com.stone.core.config.ServerConfig;

public class AgentServerConfig extends ServerConfig {
	private int internalPort;
	/** the dbSerive type */
	private String dbServiceType = "hibernate";
	private Properties dataServiceProperties = new Properties();
	private String dbConfigName;

	public String getDbServiceType() {
		return dbServiceType;
	}

	public void setDbServiceType(String dbServiceType) {
		this.dbServiceType = dbServiceType;
	}

	public Properties getDataServiceProperties() {
		return dataServiceProperties;
	}

	public void setDataServiceProperties(Properties dataServiceProperties) {
		this.dataServiceProperties = dataServiceProperties;
	}

	public String getDbConfigName() {
		return dbConfigName;
	}

	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}

	public int getInternalPort() {
		return internalPort;
	}

	public void setInternalPort(int internalPort) {
		this.internalPort = internalPort;
	}
}
