package com.stone.core.akka.test;

import com.stone.core.actor.AnnotatedUntypedActor;
import com.stone.core.annotation.ActorMethod;

public class NewActor extends AnnotatedUntypedActor {

	@ActorMethod(messageClassType = Integer.class)
	protected void handleInt(Object msg) {
		getSender().tell(msg, getSelf());
	}
}
