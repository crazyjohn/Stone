package com.stone.game.remote.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.proto.Auths.Role;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class RemoteClient {

	public static void main(String[] args) {
		Config config = ConfigFactory.load().getConfig("LOCAL");
		ActorSystem system = ActorSystem.create("ClientActor", config);
		String remotePath = "akka.tcp://RemoteActorSystem@127.0.0.1:2552/user/remoteMaster";
		ActorRef remoteProxy = system.actorOf(Props.create(RemoteActorProxy.class, remotePath), "remoteProxy");
		remoteProxy.tell("hi, remote actor!", ActorRef.noSender());
		// tell message
		remoteProxy.tell(Role.newBuilder().setName("crazyjohn").setRoleId(8888).build(), ActorRef.noSender());
		// shutdown
		// system.shutdown();
	}

}
