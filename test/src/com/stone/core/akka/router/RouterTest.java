package com.stone.core.akka.router;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RouterTest {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		ActorRef master = system.actorOf(Props.create(Master.class));
		master.tell(new Work("1"), ActorRef.noSender());
		master.tell(new Work("2"), ActorRef.noSender());
		master.tell(new Work("3"), ActorRef.noSender());
		master.tell(new Work("4"), ActorRef.noSender());
		master.tell(new Work("5"), ActorRef.noSender());
		master.tell(new Work("6"), ActorRef.noSender());
	}

}
