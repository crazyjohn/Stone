package com.stone.example.cluster.agent;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AgentFrontSystem {

	public static void startup(String port) {
		Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
				.withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).withFallback(ConfigFactory.load("agentCluster"));
		ActorSystem system = ActorSystem.create("FrontSystem", config);
		system.actorOf(Props.create(AgentFrontActor.class), "frontend");
	}
}
