package com.stone.test.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * test Lock;
 * 
 * @author crazyjohn
 *
 */
public class LockTest {
	private Lock lock = new ReentrantLock();

	public void doActionUseLock(String target, long delay) {
		lock.lock();
		try {
			try {
				Thread.sleep(delay);
				this.doAction(target);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}
	}

	public void doAction(String target) {
		System.out.println(target + " do action");
	}

	public static void main(String[] args) {
		final LockTest test = new LockTest();
		Thread a = new Thread(new Runnable() {

			@Override
			public void run() {
				test.doActionUseLock("A", 5 * 1000);
			}
		});
		a.start();
		// thread b
		Thread b = new Thread(new Runnable() {

			@Override
			public void run() {
				test.doAction("B");
			}
		});
		b.start();
		// thread c
		Thread c = new Thread(new Runnable() {

			@Override
			public void run() {
				test.doActionUseLock("c", 1);
			}
		});
		c.start();
	}

}
