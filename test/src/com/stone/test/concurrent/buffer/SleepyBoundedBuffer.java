package com.stone.test.concurrent.buffer;

public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	private static final long SLEEP_DURATION = 100;

	protected SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public void put(V v) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isFull()) {
					doPut(v);
					return;
				}
			}
			Thread.sleep(SLEEP_DURATION);
		}
	}
	
	public V take() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty()) {
					return doTake();
				}
			}
			Thread.sleep(SLEEP_DURATION);
		}
	}

}
