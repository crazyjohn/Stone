package com.stone.core.node.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class BaseActorSystem implements IActorSystem {
	/** ActorSystem */
	protected ActorSystem system;
	/** game master */
	protected ActorRef master;
	/** loggers */
	protected static Logger logger;

	protected BaseActorSystem() {
		// create logger
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public void startup() throws IOException {
		logger.info(String.format("Startup the %s", this.getClass().getSimpleName()));
	}

	@Override
	public ActorSystem getSystem() {
		return system;
	}

	@Override
	public void shutdown() {
		this.system.shutdown();
	}

	@Override
	public ActorRef getMasterActor() {
		return master;
	}

}
