package com.stone.core.bit;

public class BitTest {

	public static void main(String[] args) {
		int i = 2;
		int j = 3;
		System.out.println("i & j:" + (i & j));
		System.out.println("3 & 5:" + (3 & 5));
		System.out.println("i | j:" + (i | j));
		System.out.println(i << 2);
		System.out.println(i >> 2);
		System.out.println(0x0110);
		// byte
		System.out.println("Byte.MAX_VALUE:" + Byte.MAX_VALUE);
		// Short
		System.out.println("Short.MAX_VALUE:" + Short.MAX_VALUE);
		// Int
		System.out.println("Int.MAX_VALUE:" + Integer.MAX_VALUE);
		// Long
		System.out.println("Long.MAX_VALUE:" + Long.MAX_VALUE);

	}

}
