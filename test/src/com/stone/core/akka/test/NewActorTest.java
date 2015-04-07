package com.stone.core.akka.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import com.stone.core.akka.test.ActorTest.MyActor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;

public class NewActorTest {
	ActorSystem system;
	
	@Before
	public void start() {
		system = ActorSystem.create("system");
	}
	
	@After
	public void stop() {
		system.shutdown();
	}
	
	@Test
	public void testNewActor() throws Exception {
		final Props props = Props.create(NewActor.class);
		final TestActorRef<MyActor> ref = TestActorRef.create(system, props, "testB");
		final Future<Object> future = Patterns.ask(ref, 42, 3000);
		Assert.assertTrue(future.isCompleted());
		Assert.assertEquals(42, Await.result(future, Duration.Zero()));
	}
}
