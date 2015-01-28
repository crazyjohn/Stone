package com.stone.test.concurrent;

public class SynchronizedTest {
	private StateLock lock = new StateLock();

	static class StateLock {
		private String name = "unknown";
		private static long counter = 0;
		private final long id;

		public StateLock() {
			id = counter++;
			this.name = "StateLock-" + id;
		}

		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	/**
	 * 这种情况下的同步是无效的;
	 */
	public void lockMe() {
		synchronized (this.lock) {
			lock = new StateLock();
			System.out.println("Enter lockMe method." + lock);
		}
	}

	public static void main(String[] args) {
		SynchronizedTest test = new SynchronizedTest();
		test.lockMe();
	}

}
