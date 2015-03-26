package com.stone.core.akka.event;

import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;

public class LookupBusImpl extends LookupEventBus<MsgEnvelope, ActorRef, String> {

	@Override
	public String classify(MsgEnvelope event) {
		return event.topic;
	}

	@Override
	public int compareSubscribers(ActorRef a, ActorRef b) {
		return a.compareTo(b);
	}

	@Override
	public int mapSize() {
		return 128;
	}

	@Override
	public void publish(MsgEnvelope event, ActorRef subscriber) {
		subscriber.tell(event, ActorRef.noSender());
	}

}
