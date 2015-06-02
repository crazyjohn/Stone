package com.stone.game.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ConfigUtil;
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

	public static void main(String[] args) {
		try {
			logger.info("Begin to start StoneGameApp...");
			// db actor system
			DBActorSystem dbActorSystem = new DBActorSystem();
			// config
			GameServerConfig config = new GameServerConfig();
			ConfigUtil.loadJsConfig(config, "game_server.cfg.js");
			// init db service
			dbActorSystem.initDBService(config.getDbServiceType(), config.getDbConfigName(), config.getDataServiceProperties());
			GameActorSystem gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
			// new node
			StoneNode gameServerNode = new StoneNode();
			// init
			gameServerNode.init(config, new GameIoHandler(gameActorSystem.getMasterActor(), dbActorSystem.getMasterActor()),
					new ProtobufMessageFactory());
			// start
			gameServerNode.start();
			logger.info("StoneGameApp started.");
		} catch (Exception e) {
			logger.error("Start StoneGameApp failed.");
			// exit
			System.exit(0);
		}
	}

}
