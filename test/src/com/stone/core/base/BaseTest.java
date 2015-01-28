package com.stone.core.base;

import org.junit.Assert;
import org.junit.Test;

public class BaseTest {

	@Test
	public void integerTest() {
		// false
		Assert.assertFalse(new Integer(1) == new Integer(1));
		// true
		Assert.assertTrue(new Integer(1).equals(new Integer(1)));
	}

}
