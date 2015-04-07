package com.stone.test.concurrent.buffer;

public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	protected BoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public synchronized void put(V v) throws InterruptedException {
		while (isFull()) {
			this.wait();
		}
		doPut(v);
		this.notifyAll();
	}
	
	public synchronized V take() throws InterruptedException {
		while (isEmpty()) {
			this.wait();
		}
		V v = doTake();
		return v;
	}

}
