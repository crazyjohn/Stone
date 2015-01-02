package com.stone.test.akka;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorMain {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("System");
		final ActorRef actor = system.actorOf(Props.create(LoggerActor.class), "myChild");
		actor.tell("First actor", ActorRef.noSender());
	}

}
