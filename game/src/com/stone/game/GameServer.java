package com.stone.game;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.node.ServerNode;
import com.stone.core.node.system.IActorSystem;
import com.stone.db.DBActorSystem;
import com.stone.game.actor.GameActorSystem;
import com.stone.game.network.AgentIoHandler;
import com.stone.game.network.GameMessageFactory;

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
			final ServerNode gameServerNode = new ServerNode();
			// load config
			GameServerConfig config = gameServerNode.loadConfig(GameServerConfig.class, "game_server.cfg.js");
			// db actor system
			IActorSystem dbActorSystem = buildDBSystem(config);
			// game actor system
			IActorSystem gameActorSystem = new GameActorSystem(dbActorSystem.getMasterActor());
			// register service
			gameServerNode.registerActorSystem("GameActorSystem", gameActorSystem);
			gameServerNode.registerActorSystem("DBActorSystem", dbActorSystem);
			// connect to agent server
			connectToAgentServer(config, gameActorSystem.getMasterActor());
			// start the game node
			gameServerNode.startup();
			logger.info("GameServer started.");
		} catch (Exception e) {
			logger.error("Start GameServer failed.", e);
			// exit
			System.exit(0);
		}
	}

	private static void connectToAgentServer(GameServerConfig config, ActorRef masterActor) {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setHandler(new AgentIoHandler(masterActor));
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameCodecFactory(new GameMessageFactory())));
		// connect to master
		ConnectFuture future = connector.connect(new InetSocketAddress(config.getAgentHost(), config.getAgentPort()));
		logger.info("Start to connect to agent server...");
		// crazy?
		future.awaitUninterruptibly();
		// if connect failed, it will return a runtime exception
		future.getSession();
		logger.info("Connect to agent server succeed.");
	}
}
