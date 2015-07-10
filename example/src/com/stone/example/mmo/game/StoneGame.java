package com.stone.example.mmo.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.node.IShutdownHook;
import com.stone.core.node.IStoneService;
import com.stone.core.node.StoneServerNode;
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
public class StoneGame {
	private static Logger logger = LoggerFactory.getLogger(StoneGame.class);

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

	public static void main(String[] args) {
		try {
			logger.info("Begin to start StoneGameApp...");
			// new node
			final StoneServerNode gameServerNode = new StoneServerNode();
			// load config
			GameServerConfig config = gameServerNode.loadConfig(GameServerConfig.class, "game_server.cfg.js");
			// db actor system
			IStoneService dbActorSystem = buildDBSystem(config);
			// game actor system
			IStoneService gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
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
			gameServerNode.startup();
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
