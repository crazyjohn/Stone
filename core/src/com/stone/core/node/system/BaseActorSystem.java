package com.stone.core.node.system;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public abstract class BaseActorSystem implements IActorSystem {
	/** ActorSystem */
	protected ActorSystem system;
	/** game master */
	protected ActorRef master;
	/** loggers */
	protected static Logger logger;

	protected BaseActorSystem() {
		// create logger
		logger = LoggerFactory.getLogger(this.getClass());
		// build system
		buildActorSystem();
	}

	/**
	 * Build you actor system here;
	 * 
	 * <pre>
	 * Config config = ConfigFactory.load().getConfig(&quot;WORLD&quot;);
	 * this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
	 * this.master = system.actorOf(Props.create(WorldMaster.class), &quot;worldMaster&quot;);
	 * </pre>
	 */
	protected abstract void buildActorSystem();

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
