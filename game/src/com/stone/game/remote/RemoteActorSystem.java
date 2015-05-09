package com.stone.game.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.system.IActorHostSystem;
import com.stone.game.GameActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The remote actor system;
 * 
 * @author crazyjohn
 *
 */
public class RemoteActorSystem implements IActorHostSystem {
	/** loggers */
	protected static Logger logger = LoggerFactory.getLogger(GameActorSystem.class);
	/** ActorSystem */
	private ActorSystem system;
	/** game master */
	private ActorRef gameMaster;

	public RemoteActorSystem() {
		// load remote config
		Config config = ConfigFactory.load().getConfig("REMOTE");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
		gameMaster = system.actorOf(Props.create(RemoteActor.class), "gameMaster");
	}

	@Override
	public void shutdown() {
		this.system.shutdown();
	}

	@Override
	public ActorSystem getSystem() {
		return system;
	}

	@Override
	public ActorRef getMasterActor() {
		return gameMaster;
	}

}
