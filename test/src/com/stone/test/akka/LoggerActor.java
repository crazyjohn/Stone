package com.stone.test.akka;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoggerActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(this.getContext().system(), this);
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			log.info("Received String msg {}", msg);
			this.getSender().tell(msg, this.getSender());
		} else {
			this.unhandled(msg);
		}
	}

}
