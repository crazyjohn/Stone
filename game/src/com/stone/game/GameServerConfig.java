package com.stone.game;

import java.util.Properties;

import com.stone.core.config.ServerConfig;

/**
 * 游戏服务器配置;
 * 
 * @author crazyjohn
 *
 */
public class GameServerConfig extends ServerConfig {
	private int gameProcessorCount;
	private int dbProcessorCount;
	/** 数据服务类型 */
	private String dbServiceType = "hibernate";
	/** 数据库连接属性 */
	private Properties dataServiceProperties = new Properties();
	/** 数据库配置文件路径 */
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

	public int getGameProcessorCount() {
		return gameProcessorCount;
	}

	public void setGameProcessorCount(int gameProcessorCount) {
		this.gameProcessorCount = gameProcessorCount;
	}

	public int getDbProcessorCount() {
		return dbProcessorCount;
	}

	public void setDbProcessorCount(int dbProcessorCount) {
		this.dbProcessorCount = dbProcessorCount;
	}
}
