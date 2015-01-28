package com.stone.actor.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.stone.actor.IActorFuture;

public class ActorFuture<T> implements IActorFuture<T> {
	protected volatile boolean isReady = false;
	/** the execution result */
	protected T result;
	/** read write lock */
	protected ReadWriteLock resultLock;

	public ActorFuture() {
		resultLock = new ReentrantReadWriteLock();
	}

	@Override
	public T getResult() {
		Lock readLock = resultLock.readLock();
		readLock.lock();
		try {
			return this.result;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean isReady() {
		return isReady;
	}

	@Override
	public void setResult(T result) {
		Lock writeLock = resultLock.writeLock();
		writeLock.lock();
		try {
			this.result = result;
			ready();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void ready() {
		this.isReady = true;
	}

}
