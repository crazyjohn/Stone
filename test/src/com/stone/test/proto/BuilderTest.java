package com.stone.test.proto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.stone.proto.Humans.Human;

public class BuilderTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Human.Builder humanBuilder = Human.newBuilder();
		humanBuilder.setGuid(8888);
		Human human = humanBuilder.build();
		// assert
		assertEquals("not the same", 8888, human.getGuid());
		assertEquals(8888, humanBuilder.getGuid());
	}

}
