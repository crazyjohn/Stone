package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.system.ISystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The game message router;
 * 
 * @author crazyjohn
 *
 */
public class GameActorSystem implements ISystem {
	/** loggers */
	protected Logger logger = LoggerFactory.getLogger(GameActorSystem.class);
	/** ActorSystem */
	private ActorSystem system;
	/** game master */
	private ActorRef gameMaster;

	public GameActorSystem(ActorRef dbMaster) {
		// load GAME config
		Config config = ConfigFactory.load().getConfig("GAME");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
		gameMaster = system.actorOf(GameMaster.props(dbMaster), "GameMaster");
	}

	/**
	 * Get the actor system;
	 * 
	 * @return
	 */
	@Override
	public ActorSystem getSystem() {
		return system;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		this.system.shutdown();
	}

	@Override
	public ActorRef getMasterActor() {
		return gameMaster;
	}

}
