package com.stone.game.remote;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.IStoneActorService;
import com.stone.game.remote.server.RemoteActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The remote actor system;
 * 
 * @author crazyjohn
 *
 */
public class RemoteActorSystem implements IStoneActorService {
	/** loggers */
	protected static Logger logger = LoggerFactory.getLogger(RemoteActorSystem.class);
	/** ActorSystem */
	private ActorSystem system;
	/** game master */
	private ActorRef remoteMaster;

	public RemoteActorSystem() {
		// load remote config
		Config config = ConfigFactory.load().getConfig("REMOTE");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
		remoteMaster = system.actorOf(Props.create(RemoteActor.class), "remoteMaster");
		logger.info(remoteMaster.toString());
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
		return remoteMaster;
	}

	@Override
	public void startup() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
