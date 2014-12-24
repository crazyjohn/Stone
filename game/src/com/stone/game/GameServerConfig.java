package com.stone.game;

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
