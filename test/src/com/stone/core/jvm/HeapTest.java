package com.stone.core.jvm;

public class HeapTest {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(i);
			byte[] data = new byte[1024 * 1024 * 1024];
			System.out.println(data.length);
		}
	}

}
