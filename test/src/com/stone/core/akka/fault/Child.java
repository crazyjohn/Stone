package com.stone.core.akka.fault;

import akka.actor.UntypedActor;

public class Child extends UntypedActor {
	int state = 0;

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Exception) {
			throw (Exception) msg;
		} else if (msg instanceof Integer) {
			state = (int) msg;
		} else if (msg.equals("get")) {
			getSender().tell(state, getSelf());
		} else {
			unhandled(msg);
		}
	}

}
