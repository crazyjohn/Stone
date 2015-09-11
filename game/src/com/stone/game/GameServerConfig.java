package com.stone.game;

import java.util.Properties;

import com.stone.core.config.SlaveServerConfig;

/**
 * The game server config;
 * 
 * @author crazyjohn
 *
 */
public class GameServerConfig extends SlaveServerConfig {
	/** the dbSerive type */
	private String dbServiceType = "hibernate";
	private Properties dataServiceProperties = new Properties();
	private String dbConfigName;

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
