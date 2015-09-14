package com.stone.gate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.NodeBuilder;
import com.stone.core.node.ServerNode;
import com.stone.gate.actor.GateActorSystem;
import com.stone.gate.actor.GateProxyActorSystem;

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
			final ServerNode gateServerNode = NodeBuilder.buildCommonNode();
			// load config
			GateServerConfig config = gateServerNode.loadConfig(GateServerConfig.class, "gate_server.cfg.js");
			// init game node
			gateServerNode.init(config, new GateClientIoHandler(new GateActorSystem().getMasterActor()), new ProtobufMessageFactory());
			// build internal processor
			buildInternalProcessor(gateServerNode, config);
			// start the gate node
			gateServerNode.startup();
			logger.info("GateServer started.");
		} catch (Exception e) {
			logger.error("Start GateServer failed.", e);
			// exit
			System.exit(0);
		}
	}

	private static void buildInternalProcessor(ServerNode gateServerNode, GateServerConfig config) {
		GateProxyActorSystem system = new GateProxyActorSystem();
		ServerIoProcessor ioProcessor = new ServerIoProcessor(config.getBindIp(), config.getInternalPort(), new GateInternalIoHandler(
				system.getMasterActor()), new GameCodecFactory(new ProtobufMessageFactory()));
		gateServerNode.addIoProcessor("internalProcessor", ioProcessor);
	}
}
