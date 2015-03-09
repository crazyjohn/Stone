package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.system.ISystem;

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
		system = ActorSystem.create("GameActorSystem");
		gameMaster = system.actorOf(GameMaster.props(dbMaster));
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
