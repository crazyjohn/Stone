package com.stone.core.akka.fault;

import scala.concurrent.duration.Duration;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;

public class Supervisor extends UntypedActor {
	private static int counter = 0;
	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), new Function<Throwable, Directive>() {

		@Override
		public Directive apply(Throwable t) throws Exception {
			if (t instanceof ArithmeticException) {
				return SupervisorStrategy.resume();
			} else if (t instanceof NullPointerException) {
				return SupervisorStrategy.restart();
			} else if (t instanceof IllegalArgumentException) {
				return SupervisorStrategy.stop();
			} else {
				return SupervisorStrategy.escalate();
			}
		}

	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Props) {
			counter++;
			getSender().tell(getContext().actorOf((Props) msg, "child" + counter), getSelf());
		} else {
			unhandled(msg);
		}
	}

}
