package com.id.test;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

public class Publisher extends UntypedActor {
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();

	@Override
	public void preStart() {
		mediator.tell(new DistributedPubSubMediator.Publish("A", "aaa"), getSelf());
	}

	@Override
	public void onReceive(Object arg0) throws Exception {

	}

}
