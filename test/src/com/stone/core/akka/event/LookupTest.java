package com.stone.core.akka.event;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

public class LookupTest {
	ActorSystem system;
	JavaTestKit kit;

	@Before
	public void start() {
		system = ActorSystem.create("TestSystem");
		kit = new JavaTestKit(system);
	}

	@After
	public void stop() {
		kit.shutdown(system);
	}

	@Test
	public void testLookup() {
		LookupBusImpl lookupBus = new LookupBusImpl();
		ActorRef testActor = system.actorOf(Props.create(TestActor.class));
		lookupBus.subscribe(testActor, "greetings");
		lookupBus.publish(new MsgEnvelope("time", System.currentTimeMillis()));
		lookupBus.publish(new MsgEnvelope("greetings", "hello"));
	}
}
