package com.stone.example.cluster.agent;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AgentBackSystem {

	public static void startup(String port) {
		// config
		Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).withFallback(ConfigFactory.load("agentCluster"));
		ActorSystem system = ActorSystem.create("ClusterSystem", config);
		system.actorOf(Props.create(AgentBackActor.class));
	}
}
