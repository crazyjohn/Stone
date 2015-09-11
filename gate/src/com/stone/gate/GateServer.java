package com.stone.gate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.config.ServerConfig;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.NodeBuilder;
import com.stone.core.node.info.IMasterServerNode;
import com.stone.gate.actor.GateActorSystem;

/**
 * The gate server;
 * <p>
 * Manage the external client session;
 * 
 * @author crazyjohn
 *
 */
public class GateServer {
	private static Logger logger = LoggerFactory.getLogger(GateServer.class);

	public static void main(String[] args) {
		try {
			logger.info("Begin to start GateServer...");
			// new node
			final IMasterServerNode gateServerNode = NodeBuilder.buildMasterNode();
			// load config
			ServerConfig config = gateServerNode.loadConfig(ServerConfig.class, "gate_server.cfg.js");
			// init game node
			gateServerNode.init(config, new GateIoHandler(new GateActorSystem().getMasterActor()), new ProtobufMessageFactory());
			// start the world node
			gateServerNode.startup();
			logger.info("GateServer started.");
			// test shutdown
			// Thread.sleep(5 * 60 * 1000);
			// gameServerNode.shutdown();
		} catch (Exception e) {
			logger.error("Start GateServer failed.", e);
			// exit
			System.exit(0);
		}
	}
}
