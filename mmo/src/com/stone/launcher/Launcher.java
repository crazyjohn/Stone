package com.stone.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.agent.AgentServer;
import com.stone.game.GameServer;
import com.stone.world.WorldServer;

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
		logger.info("Begin to start the mmo server group...");
		// start the world server
		AgentServer.main(args);
		// start the world server
		WorldServer.main(args);
		// start the game server
		GameServer.main(args);
		logger.info("The mmo server group started.");
	}

}
