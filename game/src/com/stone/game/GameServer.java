package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.StoneServerNode;
import com.stone.core.node.service.IStoneActorService;
import com.stone.db.DBActorSystem;

/**
 * The mmo game server, use stone engine;
 * 
 * 
 * @author crazyjohn
 *
 */
public class GameServer {
	private static Logger logger = LoggerFactory.getLogger(GameServer.class);

	/**
	 * Build db system;
	 * 
	 * @param config
	 * @return
	 */
	private static IStoneActorService buildDBSystem(GameServerConfig config) {
		DBActorSystem dbActorSystem = new DBActorSystem();
		// init db service
		dbActorSystem.initDBService(config.getDbServiceType(), config.getDbConfigName(), config.getDataServiceProperties());
		return dbActorSystem;
	}

	public static void main(String[] args) {
		try {
			logger.info("Begin to start Stone engine...");
			// new node
			final StoneServerNode gameServerNode = new StoneServerNode();
			// load config
			GameServerConfig config = gameServerNode.loadConfig(GameServerConfig.class, "game_server.cfg.js");
			// db actor system
			IStoneActorService dbActorSystem = buildDBSystem(config);
			// game actor system
			IStoneActorService gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
			// register service
			gameServerNode.registerService("GameActorSystem", gameActorSystem);
			gameServerNode.registerService("DBActorSystem", dbActorSystem);
			// init game node
			gameServerNode.init(config, new GameIoHandler(gameActorSystem.getMasterActor(), dbActorSystem.getMasterActor()),
					new ProtobufMessageFactory());
			// start the game node
			gameServerNode.startup();
			logger.info("Stone engine started.");
			// test shutdown
			Thread.sleep(5 * 60 * 1000);
			gameServerNode.shutdown();
		} catch (Exception e) {
			logger.error("Start Stone engine failed.");
			// exit
			System.exit(0);
		}
	}

}
