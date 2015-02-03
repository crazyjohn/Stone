package com.stone.actor.future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.stone.actor.call.IActorCallback;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.actor.system.IActorSystem;
import com.stone.core.annotation.GuardedByUnit;
import com.stone.core.annotation.ThreadSafeUnit;

/**
 * 基础的future实现;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
@ThreadSafeUnit
public class ActorFuture<T> implements IActorFuture<T> {
	protected volatile boolean isReady = false;
	/** the execution result */
	@GuardedByUnit(whoCareMe = "ReadWriteLock")
	protected T result;
	/** read write lock */
	protected ReadWriteLock resultLock;
	/** listeners */
	@GuardedByUnit(whoCareMe = "this")
	protected List<IActorFutureListener<T>> listeners = new LinkedList<IActorFutureListener<T>>();
	protected IActorSystem actorSystem;

	public ActorFuture() {
		resultLock = new ReentrantReadWriteLock();
	}

	@Override
	public T getResult() {
		// just balking(balking pattern)
		if (!this.isReady) {
			return null;
		}
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
			// notify listeners
			notifyListeners(this);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * 通知监听器;
	 * 
	 * @param actorFuture
	 */
	private synchronized void notifyListeners(final IActorFuture<T> actorFuture) {
		for (final IActorFutureListener<T> eachListener : this.listeners) {
			// has target?
			actorSystem.dispatch(eachListener.getTarget(), new IActorCallback<T>() {
				@Override
				public void doCallback(T result) {
					eachListener.onComplete(actorFuture);
				}
			}, actorFuture.getResult());
		}
	}

	@Override
	public synchronized void ready() {
		this.isReady = true;
		// notify all, i you want do this, make sure you got the wait object's
		// lock
		this.notifyAll();
	}

	@Override
	public synchronized void addListener(IActorFutureListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public synchronized void removeListener(IActorFutureListener<T> listener) {
		listeners.remove(listener);
	}

	@Override
	public synchronized T awaitResult() throws InterruptedException {
		while (!isReady) {
			this.wait();
		}
		return result;
	}

	@Override
	public T awaitResult(long timeout) throws InterruptedException {
		while (!isReady) {
			this.wait(timeout);
		}
		return result;
	}

}
