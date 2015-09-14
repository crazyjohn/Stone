package com.stone.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.agent.actor.AgentActorSystem;
import com.stone.core.codec.GameCodecFactory;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.NodeBuilder;
import com.stone.core.node.ServerNode;

/**
 * The gate server;
 * <p>
 * Manage the external client session;
 * 
 * @author crazyjohn
 *
 */
public class AgentServer {
	private static Logger logger = LoggerFactory.getLogger(AgentServer.class);

	/**
	 * Build the internal processor;
	 * 
	 * @param gateServerNode
	 * @param config
	 */
	private static void buildInternalProcessor(ServerNode gateServerNode, AgentServerConfig config, ActorRef gateMaster) {
		ServerIoProcessor ioProcessor = new ServerIoProcessor(config.getBindIp(), config.getInternalPort(), new AgentInternalIoHandler(gateMaster),
				new GameCodecFactory(new ProtobufMessageFactory()));
		gateServerNode.addIoProcessor("internalProcessor", ioProcessor);
	}

	public static void main(String[] args) {
		try {
			logger.info("Begin to start GateServer...");
			// new node
			final ServerNode gateServerNode = NodeBuilder.buildCommonNode();
			// load config
			AgentServerConfig config = gateServerNode.loadConfig(AgentServerConfig.class, "gate_server.cfg.js");
			// init game node
			AgentActorSystem gateSystem = new AgentActorSystem();
			gateServerNode.init(config, new AgentExternalIoHandler(gateSystem.getMasterActor()), new ProtobufMessageFactory());
			// build internal processor
			buildInternalProcessor(gateServerNode, config, gateSystem.getMasterActor());
			// start the gate node
			gateServerNode.startup();
			logger.info("GateServer started.");
		} catch (Exception e) {
			logger.error("Start GateServer failed.", e);
			// exit
			System.exit(0);
		}
	}

}
