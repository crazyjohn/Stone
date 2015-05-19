package com.stone.test.concurrent.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameDeadLock {

	public static void main(String[] args) throws InterruptedException {
		// fucker thread
		final Lock lock = new ReentrantLock();
		Thread fucker = new Thread("fucker") {
			@Override
			public void run() {
				lock.lock();
				System.out.println("Fucker locked the lock, but did not unlock.");
				// never ending
				while (true) {}
			}
		};
		fucker.start();
		// sleep
		Thread.sleep(1000L);
		// want lock
		for (int i = 0; i <= 5; i++) {
			Thread ioThread = new Thread("IoThread " + i) {
				@Override
				public void run() {
					lock.lock();
					System.out.println(Thread.currentThread() + "locked.");
				}
			};
			ioThread.start();
		}
	}

}
