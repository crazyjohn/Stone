package com.stone.core.base;

public class Launcher {
	public static void main(String[] args) {
		MockServer.main(new String[0]);
		MockServer.main(new String[0]);
		MockServer.main(new String[0]);
	}

	static class MockServer {
		public static void main(String[] args) {
			System.out.println("mock server");
		}
	}
}
