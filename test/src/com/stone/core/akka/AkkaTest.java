package com.stone.core.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
//import static akka.pattern.Patterns.ask;
//import scala.concurrent.Future;

public class AkkaTest {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("MySystem");
		ActorRef actor = system.actorOf(Props.create(LoggerActor.class));
		actor.tell("tell you something.", ActorRef.noSender());
		// ask pattern
		// Future<?> result = ask(actor,"", 1000);
	}

}
