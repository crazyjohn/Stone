package com.stone.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ConfigUtil;
import com.stone.core.config.ServerConfig;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.ServerNode;
import com.stone.core.node.system.IActorSystem;
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
			// load config
			ServerConfig config = ConfigUtil.loadConfig(ServerConfig.class, "world_server.cfg.js");
			// new node
			final ServerNode worldServerNode = new ServerNode(config.getName());
			// init game node
			IActorSystem worldSystem = new WorldActorSystem();
			worldServerNode.registerActorSystem("WorldActorSystem", worldSystem);
			worldServerNode.init(config, new WorldIoHandler(worldSystem.getMasterActor()), new ProtobufMessageFactory());
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
