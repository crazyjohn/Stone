package com.id.test;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

public class PublishNode extends UntypedActor {
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	{
		mediator.tell(new DistributedPubSubMediator.Subscribe("A", getSelf()), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(msg.toString());
		getSender().tell("ccc", getSelf());
	}
}
