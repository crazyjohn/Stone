package com.stone.agent.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.system.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AgentActorSystem extends BaseActorSystem {
	private final ActorRef dbMaster;

	public AgentActorSystem(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
		Config config = ConfigFactory.load().getConfig("Gate");
		this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
		this.master = system.actorOf(Props.create(AgentMaster.class, this.dbMaster), "gateMaster");
	}

	@Override
	protected void buildActorSystem() {
		
	}
}
