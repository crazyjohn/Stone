package com.stone.core.akka.net;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		// get tcp manager
		ActorRef tcpManager = Tcp.get(system).getManager();
		Props props = Props.create(Acceptor.class, tcpManager);
		ActorRef acceptor = system.actorOf(props, "acceptor");
		acceptor.tell(12345, ActorRef.noSender());
	}

}
