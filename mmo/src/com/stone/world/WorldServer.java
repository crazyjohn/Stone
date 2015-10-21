package com.stone.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ServerConfig;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.ServerNode;
import com.stone.world.actor.WorldActorSystem;
import com.stone.world.network.WorldIoHandler;

/**
 * The world server;
 * 
 * @author crazyjohn
 *
 */
public class WorldServer {
	private static Logger logger = LoggerFactory.getLogger(WorldServer.class);

	public static void main(String[] args) {
		try {
			logger.info("Begin to start WorldServer...");
			// new node
			final ServerNode worldServerNode = new ServerNode();
			// load config
			ServerConfig config = worldServerNode.loadConfig(ServerConfig.class, "world_server.cfg.js");
			// init game node
			worldServerNode.init(config, new WorldIoHandler(new WorldActorSystem().getMasterActor()), new ProtobufMessageFactory());
			// start the world node
			worldServerNode.startup();
			logger.info("WorldServer started.");
		} catch (Exception e) {
			logger.error("Start WorldServer failed.", e);
			// exit
			System.exit(0);
		}
	}

}
