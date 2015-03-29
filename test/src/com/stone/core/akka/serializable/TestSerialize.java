package com.stone.core.akka.serializable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.serialization.Serialization;
import akka.serialization.SerializationExtension;
import akka.serialization.Serializer;

public class TestSerialize {
	ActorSystem system;

	@Before
	public void start() {
		system = ActorSystem.create("example");
	}

	@After
	public void stop() {
		system.shutdown();
	}

	@Test
	public void testSerialize() {
		Serialization serialization = SerializationExtension.get(system);
		String original = "woohoo";
		Serializer serializer = serialization.findSerializerFor(original);
		// serialize
		byte[] bytes = serializer.toBinary(original);
		// deserialize
		String back = (String) serializer.fromBinary(bytes);
		Assert.assertEquals(back, original);
	}
}
