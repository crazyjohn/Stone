package com.stone.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.agent.AgentServer;
import com.stone.game.GameServer;

/**
 * Launch the server group in same process;
 * 
 * @author crazyjohn
 *
 */
public class Launcher {
	/** the logger */
	private static Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		// start the agent server
		logger.info("Begin to start the server group...");
		AgentServer.main(args);
		// start the game server
		GameServer.main(args);
		logger.info("ServerGroup started.");
	}

}
