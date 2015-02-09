package com.stone.core.akka;

import static akka.pattern.Patterns.ask;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.concurrent.Future;

public class AkkaFutureTest {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("MySystem");
		ActorRef actor = system.actorOf(Props.create(LoggerActor.class));
		// ask pattern
		Future<?> result = ask(actor, "", 1000);
		System.out.println(result.isCompleted());
	}

}
