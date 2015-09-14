package com.stone.gate.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.system.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class GateProxyActorSystem extends BaseActorSystem {
	public GateProxyActorSystem() {
		Config config = ConfigFactory.load().getConfig("Proxy");
		this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
		this.master = system.actorOf(Props.create(GateProxyMaster.class), "gateProxyMaster");
	}
}
