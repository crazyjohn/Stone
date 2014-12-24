package com.stone.game;

import java.io.IOException;

import javax.script.ScriptException;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.config.ConfigUtil;
import com.stone.core.log.ILogger;
import com.stone.core.log.Loggers;
import com.stone.core.net.ServerProcess;
import com.stone.core.processor.IDispatcher;
import com.stone.core.service.IService;
import com.stone.db.dispatch.DBDispatcher;
import com.stone.game.msg.GameDispatcher;

/**
 * 游戏服务器;
 * 
 * @author crazyjohn
 *
 */
public class GameServer implements IService {
	private static ILogger logger = Loggers.getLogger(GameServer.class);
	private GameServerConfig config;
	private String configPath;
	protected ServerProcess externalProcess;
	protected GameDispatcher mainDispatcher;
	protected IDispatcher dbDispatcher;

	public GameServer(String configPath) {
		this.configPath = configPath;
	}

	@Override
	public void init() throws ScriptException, IOException {
		config = new GameServerConfig();
		ConfigUtil.loadJsConfig(config, configPath);
		mainDispatcher = new GameDispatcher(config.getGameProcessorCount());
		dbDispatcher = new DBDispatcher(config.getDbProcessorCount());
		mainDispatcher.setDbDispatcher(dbDispatcher);
		// 对外服务
		externalProcess = new ServerProcess(config.getBindIp(),
				config.getPort(), new GameIoHandler(mainDispatcher),
				new GameCodecFactory());

	}

	@Override
	public void start() throws IOException {
		mainDispatcher.start();
		dbDispatcher.start();
		externalProcess.start();
		// shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				shutdown();
			}
		}));
	}

	@Override
	public void shutdown() {
		externalProcess.shutdown();
		dbDispatcher.stop();
		mainDispatcher.stop();
	}

	public static void main(String[] args) {
		logger.info("Begin to start Game Server");
		try {
			GameServer gameserver = new GameServer("game_server.cfg.js");
			gameserver.init();
			gameserver.start();
			logger.info("Game Server started");
		} catch (Exception e) {
			logger.error("Failed to start server", e);
			System.exit(0);
		}
		
	}

}
