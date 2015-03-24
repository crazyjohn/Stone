package com.stone.core.akka.test;

import org.junit.Assert;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.testkit.TestActorRef;

public class MyActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg.equals("say42")) {
			getSender().tell(42, getSelf());
		} else if (msg instanceof Exception) {
			throw (Exception) msg;
		}
	}

	public boolean testMe() {
		return true;
	}

	@Test
	public void demonstrateTestActorRef() {
		final ActorSystem system = ActorSystem.create("testSystem");
		final Props props = Props.create(MyActor.class);
		final TestActorRef<MyActor> ref = TestActorRef.create(system, props, "testA");
		final MyActor actor = ref.underlyingActor();
		Assert.assertTrue(actor.testMe());
	}

}
