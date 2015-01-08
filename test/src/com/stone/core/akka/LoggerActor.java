package com.stone.core.akka;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoggerActor extends UntypedActor {
	LoggingAdapter logger = Logging.getLogger(this.getContext().system(), this);
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			logger.info("Received String message: {}", msg);
			//this.getSender().tell(msg, this.getSelf());
		} else {
			this.unhandled(msg);
		}
	}

}
