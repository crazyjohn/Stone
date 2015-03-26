package com.stone.core.akka.event;

import akka.actor.UntypedActor;

public class TestActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			System.out.println(msg);
		} else {
			unhandled(msg);
		}
	}

}
