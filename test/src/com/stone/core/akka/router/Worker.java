package com.stone.core.akka.router;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class Worker extends UntypedActor {
	private final String name;
	
	public Worker(String name) {
		this.name = name;
	}
	public static Props props(String name) {
		return Props.create(Worker.class, name);
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Work) {
			System.out.println(String.format("%s received msg: %s", name, ((Work)msg).getPayload()));
		}
	}

}
