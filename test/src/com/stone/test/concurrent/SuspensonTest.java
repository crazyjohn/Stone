package com.stone.test.concurrent;

import java.util.LinkedList;

/**
 * Guarder and suspenson pattern;<br>
 * 测试目的：了解java中wait和notify的使用方法和原理;<br>
 * 使用方法：<br>
 * 1. java中的wait和notify的使用的前提是要先获得this对象的锁,
 * 然后在调用wait以后调用线程会进入this对象的waitset，同时释放锁，此时其它调用线程可以获得this对象的锁。<br>
 * 2. 使用notify也要先获得this对象的锁
 * ，而且notify被调用以后不是马上就会释放锁，这和wait有点不一样，而是要等到synchronized块结束以后才释放锁
 * 
 * @author crazyjohn
 *
 */
public class SuspensonTest {
	/** request queue */
	private LinkedList<IRequest> requestQueue = new LinkedList<IRequest>();
	/** capacity */
	private int capacity;

	/**
	 * request
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IRequest {
	}

	/**
	 * get request
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	protected synchronized IRequest getRequest() throws InterruptedException {
		while (requestQueue.size() <= 0) {
			this.wait();
		}
		IRequest request = requestQueue.removeFirst();
		capacity--;
		this.notifyAll();
		return request;
	}

	protected synchronized void synchronizedLockTest() {
		System.out.println("Enter synchronizedLockTest method.");
	}

	/**
	 * put request;
	 * 
	 * @param request
	 * @throws InterruptedException
	 */
	protected synchronized void putRequest(IRequest request) throws InterruptedException {
		while (requestQueue.size() >= capacity) {
			this.wait();
		}
		requestQueue.addLast(request);
		this.notifyAll();
	}

	public static void main(String[] args) {
		final SuspensonTest test = new SuspensonTest();
		// get thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					test.getRequest();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		test.synchronizedLockTest();
	}

}
