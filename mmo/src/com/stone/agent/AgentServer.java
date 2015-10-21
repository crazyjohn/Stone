package com.stone.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.agent.actor.AgentActorSystem;
import com.stone.agent.network.AgentExternalIoHandler;
import com.stone.agent.network.AgentExternalMessageFactory;
import com.stone.agent.network.AgentInternalIoHandler;
import com.stone.agent.network.AgentInternalMessageFactory;
import com.stone.core.codec.GameCodecFactory;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.ServerNode;
import com.stone.core.node.system.IActorSystem;
import com.stone.db.DBActorSystem;

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
				new GameCodecFactory(new AgentInternalMessageFactory()));
		gateServerNode.registerIoProcessor("internalProcessor", ioProcessor);
	}

	private static IActorSystem buildDBSystem(AgentServerConfig config) {
		DBActorSystem dbActorSystem = new DBActorSystem();
		// init db service
		dbActorSystem.initDBService(config.getDbServiceType(), config.getDbConfigName(), config.getDataServiceProperties());
		return dbActorSystem;
	}

	public static void main(String[] args) {
		try {
			logger.info("Begin to start AgentServer...");
			// new node
			final ServerNode gateServerNode = new ServerNode();
			// load config
			AgentServerConfig config = gateServerNode.loadConfig(AgentServerConfig.class, "agent_server.cfg.js");
			// db actor system
			IActorSystem dbActorSystem = buildDBSystem(config);
			// init game node
			AgentActorSystem gateSystem = new AgentActorSystem(dbActorSystem.getMasterActor());
			gateServerNode.init(config, new AgentExternalIoHandler(gateSystem.getMasterActor()), new AgentExternalMessageFactory());
			// build internal processor
			buildInternalProcessor(gateServerNode, config, gateSystem.getMasterActor());
			// start the gate node
			gateServerNode.startup();
			logger.info("AgentServer started.");
		} catch (Exception e) {
			logger.error("Start GateServer failed.", e);
			// exit
			System.exit(0);
		}
	}

}
