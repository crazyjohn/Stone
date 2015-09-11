package com.stone.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ServerConfig;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.NodeBuilder;
import com.stone.core.node.ServerNode;
import com.stone.world.actor.WorldActorSystem;

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
			final ServerNode worldServerNode = NodeBuilder.buildCommonNode();
			// load config
			ServerConfig config = worldServerNode.loadConfig(ServerConfig.class, "world_server.cfg.js");
			// init game node
			worldServerNode.init(config, new WorldIoHandler(new WorldActorSystem().getMasterActor()), new ProtobufMessageFactory());
			// start the world node
			worldServerNode.startup();
			logger.info("WorldServer started.");
			// test shutdown
			// Thread.sleep(5 * 60 * 1000);
			// gameServerNode.shutdown();
		} catch (Exception e) {
			logger.error("Start WorldServer failed.", e);
			// exit
			System.exit(0);
		}
	}

}
