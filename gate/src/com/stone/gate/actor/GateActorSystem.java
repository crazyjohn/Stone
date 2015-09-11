package com.stone.gate.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.service.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class GateActorSystem extends BaseActorSystem {
	public GateActorSystem() {
		Config config = ConfigFactory.load().getConfig("GATE");
		this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
		this.master = system.actorOf(Props.create(GateMaster.class), "gateMaster");
	}
}
