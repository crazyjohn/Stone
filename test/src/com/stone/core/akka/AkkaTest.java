package com.stone.core.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaTest {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("MySystem");
		ActorRef actor = system.actorOf(Props.create(LoggerActor.class));
		actor.tell("tell you something.", null);
	}

}
