package com.stone.test.concurrent;

public class SynchronizedBlocking {
	
	public static void main(String[] args) throws InterruptedException {
		Thread waiter1 = new Thread() {
			@Override
			public void run() {
				justSleep();
			}
		};
		Thread waiter2 = new Thread() {
			@Override
			public void run() {
				justSleep();
			}
		};
		waiter1.start();
		waiter2.start();
		waiter1.interrupt();
		waiter2.interrupt();
		waiter1.join();
		waiter2.join();
	}
	
	public synchronized static void justSleep() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}

}
