package com.stone.actor.future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.stone.actor.listener.IActorFutureListener;
import com.stone.core.annotation.GuardedByUnit;
import com.stone.core.annotation.NotThreadSafeUnit;

public class ActorFuture<T> implements IActorFuture<T> {
	protected volatile boolean isReady = false;
	/** the execution result */
	protected T result;
	/** read write lock */
	protected ReadWriteLock resultLock;
	/** listeners */
	@GuardedByUnit(whoCareMe = "nobody")
	@NotThreadSafeUnit
	protected List<IActorFutureListener<T>> listeners = new LinkedList<IActorFutureListener<T>>();

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

	@Override
	public void addListener(IActorFutureListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IActorFutureListener<T> listener) {
		listeners.remove(listener);
	}

}
