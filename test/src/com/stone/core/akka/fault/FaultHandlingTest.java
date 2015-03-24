package com.stone.core.akka.fault;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;

public class FaultHandlingTest {
	static ActorSystem system;
	Duration timeout = Duration.create(5, TimeUnit.SECONDS);

	@BeforeClass
	public static void start() {
		system = ActorSystem.create("test");
	}

	@AfterClass
	public static void cleanup() {
		JavaTestKit.shutdownActorSystem(system);
	}

	@Test
	public void mustEmploySupervisorStrategy() throws Exception {
		// supervisor
		Props superProps = Props.create(Supervisor.class);
		ActorRef supervisor = system.actorOf(superProps, "supervisor");
		// child
		ActorRef child = (ActorRef) Await.result(Patterns.ask(supervisor, Props.create(Child.class), 5000), timeout);
		child.tell(42, ActorRef.noSender());
		Assert.assertEquals(Await.result(Patterns.ask(child, "get", 5000), timeout), 42);
		// exception
		child.tell(new ArithmeticException(), ActorRef.noSender());
		Assert.assertEquals(Await.result(Patterns.ask(child, "get", 5000), timeout), 42);
		// null exception get 0
		child.tell(new NullPointerException(), ActorRef.noSender());
		Assert.assertEquals(Await.result(Patterns.ask(child, "get", 5000), timeout), 0);
		// illegal exception
		final TestProbe probe = new TestProbe(system);
		probe.watch(child);
		child.tell(new IllegalArgumentException(), ActorRef.noSender());
		Assert.assertSame(Terminated.class, probe.expectMsgClass(Terminated.class).getClass());
		// another child
		ActorRef anotherChild = (ActorRef) Await.result(Patterns.ask(supervisor, Props.create(Child.class), 5000), timeout);
		probe.watch(anotherChild);
		Assert.assertEquals(Await.result(Patterns.ask(anotherChild, "get", 5000), timeout), 0);
		// exception to escalate
		child.tell(new Exception(), ActorRef.noSender());
		Assert.assertSame(Terminated.class, probe.expectMsgClass(Terminated.class).getClass());
	}
}
