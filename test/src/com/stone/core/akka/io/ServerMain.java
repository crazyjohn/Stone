package com.stone.core.akka.io;

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;

public class ServerMain {

	public static void main(String[] args) throws InterruptedException {
		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef tcpManager = Tcp.get(system).getManager();
		// create acceptor
		ActorRef acceptor = system.actorOf(Props.create(Server.class, tcpManager), "Acceptor");
		System.out.println(acceptor);
		// sleep
		Thread.sleep(5 * 1000l);
		// connector
		ActorRef connector = system.actorOf(Props.create(Client.class, new InetSocketAddress("localhost", 8888), tcpManager), "Connector");
		System.out.println(connector);
	}

}
