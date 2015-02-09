package com.stone.core.akka.hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloActorTest {

	public static class HelloActor extends UntypedActor {
		final ActorRef worldActor = getContext().actorOf(Props.create(WorldActor.class));

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg == "start") {
				worldActor.tell("Hello", getSelf());
			} else if (msg instanceof String) {
				System.out.println(String.format("Received msg '%s'", msg));
			} else {
				unhandled(msg);
			}
		}

	}

	public static class WorldActor extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof String) {
				getSender().tell(msg + " world!", getSelf());
			} else {
				unhandled(msg);
			}

		}

	}

	public static void main(String[] args) {
		// system
		final ActorSystem system = ActorSystem.create("gameSystem");
		system.actorOf(Props.create(HelloActor.class)).tell("start", null);
	}

}
