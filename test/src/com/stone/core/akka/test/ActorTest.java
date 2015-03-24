package com.stone.core.akka.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;

public class ActorTest {
	public static class MyActor extends UntypedActor {

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

	}

	ActorSystem system;

	@Before
	public void start() {
		system = ActorSystem.create("testSystem");
	}

	@After
	public void stop() {
		JavaTestKit.shutdownActorSystem(system);
	}

	@Test
	public void demonstrateTestActorRef() {
		final ActorSystem system = ActorSystem.create("testSystem");
		final Props props = Props.create(MyActor.class);
		final TestActorRef<MyActor> ref = TestActorRef.create(system, props, "testA");
		final MyActor actor = ref.underlyingActor();
		Assert.assertTrue(actor.testMe());
	}

	@Test
	public void testActorBehavior() throws Exception {
		final Props props = Props.create(MyActor.class);
		final TestActorRef<MyActor> ref = TestActorRef.create(system, props, "testB");
		final Future<Object> future = Patterns.ask(ref, "say42", 3000);
		Assert.assertTrue(future.isCompleted());
		Assert.assertEquals(42, Await.result(future, Duration.Zero()));
	}
}
