package com.stone.example;

import java.io.IOException;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ConfigUtil;
import com.stone.core.node.IShutdownHook;
import com.stone.core.node.IStoneService;
import com.stone.core.node.StoneNode;
import com.stone.db.DBActorSystem;
import com.stone.game.GameActorSystem;
import com.stone.game.GameIoHandler;
import com.stone.game.GameServerConfig;
import com.stone.game.msg.ProtobufMessageFactory;

/**
 * The StoneGameApp;
 * 
 * 
 * @author crazyjohn
 *
 */
public class StoneGameApp {
	private static Logger logger = LoggerFactory.getLogger(StoneGameApp.class);

	/**
	 * Build db system;
	 * 
	 * @param config
	 * @return
	 */
	private static IStoneService buildDBSystem(GameServerConfig config) {
		DBActorSystem dbActorSystem = new DBActorSystem();
		// init db service
		dbActorSystem.initDBService(config.getDbServiceType(), config.getDbConfigName(), config.getDataServiceProperties());
		return dbActorSystem;
	}

	/**
	 * Load config;
	 * 
	 * @param string
	 * @return
	 * @throws ScriptException
	 * @throws IOException
	 */
	private static GameServerConfig loadConfig(String string) throws ScriptException, IOException {
		GameServerConfig config = new GameServerConfig();
		ConfigUtil.loadJsConfig(config, "game_server.cfg.js");
		return config;
	}

	public static void main(String[] args) {
		try {
			logger.info("Begin to start StoneGameApp...");
			// load config
			GameServerConfig config = loadConfig("game_server.cfg.js");
			// db actor system
			IStoneService dbActorSystem = buildDBSystem(config);
			// game actor system
			IStoneService gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
			// new node
			final StoneNode gameServerNode = new StoneNode();
			// register service
			gameServerNode.registerService("GameActorSystem", gameActorSystem);
			gameServerNode.registerService("DBActorSystem", dbActorSystem);
			// shutdown hook
			gameServerNode.addShutdownHook(new IShutdownHook() {
				@Override
				public void run() {
					gameServerNode.shutdown();
				}
			});
			// init game node
			gameServerNode.init(config, new GameIoHandler(gameActorSystem.getMasterActor(), dbActorSystem.getMasterActor()),
					new ProtobufMessageFactory());
			// start the game node
			gameServerNode.start();
			logger.info("StoneGameApp started.");
			// test shutdown
			Thread.sleep(5 * 60 * 1000);
			gameServerNode.shutdown();
		} catch (Exception e) {
			logger.error("Start StoneGameApp failed.");
			// exit
			System.exit(0);
		}
	}

}
