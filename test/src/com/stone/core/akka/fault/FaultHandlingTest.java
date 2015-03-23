package com.stone.core.akka.fault;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;

public class FaultHandlingTest {
	static ActorSystem system;
	Duration timeout = Duration.create(5, TimeUnit.SECONDS);

	@BeforeClass
	public static void start() {
		//system = ActorSystem.create("test", Akkas);
	}

	@AfterClass
	public static void cleanup() {
		JavaTestKit.shutdownActorSystem(system);
	}

	@Test
	public void mustEmploySupervisorStrategy() {
	}
}
