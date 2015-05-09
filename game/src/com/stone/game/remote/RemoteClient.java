package com.stone.game.remote;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class RemoteClient {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("ClientActor");
		ActorSelection selection = system.actorSelection("akka.tcp://RemoteActorSystem@127.0.0.1:2552/user/remoteMaster");
		selection.tell("hi, remote actor!", ActorRef.noSender());
	}

}
