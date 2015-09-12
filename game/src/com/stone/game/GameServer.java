package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.NodeBuilder;
import com.stone.core.node.info.ISlaveServerNode;
import com.stone.core.node.service.IActorSystem;
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
	private static IActorSystem buildDBSystem(GameServerConfig config) {
		DBActorSystem dbActorSystem = new DBActorSystem();
		// init db service
		dbActorSystem.initDBService(config.getDbServiceType(), config.getDbConfigName(), config.getDataServiceProperties());
		return dbActorSystem;
	}

	public static void main(String[] args) {
		try {
			logger.info("Begin to start GameServer...");
			// new node
			final ISlaveServerNode gameServerNode = NodeBuilder.buildSlaveNode();
			// load config
			GameServerConfig config = gameServerNode.loadConfig(GameServerConfig.class, "game_server.cfg.js");
			// db actor system
			IActorSystem dbActorSystem = buildDBSystem(config);
			// game actor system
			IActorSystem gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
			// register service
			gameServerNode.registerActorSystem("GameActorSystem", gameActorSystem);
			gameServerNode.registerActorSystem("DBActorSystem", dbActorSystem);
			// init game node
			gameServerNode.init(config, new GameIoHandler(gameActorSystem.getMasterActor(), dbActorSystem.getMasterActor()),
					new ProtobufMessageFactory());
			// start the game node
			gameServerNode.startup();
			// connect to master
			gameServerNode.connectToMasters(config);
			logger.info("GameServer started.");
			// test shutdown
			// Thread.sleep(5 * 60 * 1000);
			// gameServerNode.shutdown();
		} catch (Exception e) {
			logger.error("Start GameServer failed.", e);
			// exit
			System.exit(0);
		}
	}
}
