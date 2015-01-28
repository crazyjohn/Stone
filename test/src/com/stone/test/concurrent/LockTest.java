package com.stone.test.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 关于锁的互斥测试;<br>
 * 测试目的: 锁的作用范围?<br>
 * 测试结果: 只要线程对方法的调用绕开lock模块, 那么lock就是shit;不过好像是废话;
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
