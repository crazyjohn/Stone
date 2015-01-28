package com.stone.actor.future;

import com.stone.actor.listener.IActorFutureListener;

public interface IActorFuture<T> {

	public T getResult();

	public void setResult(T result);

	public boolean isReady();

	public void ready();

	public void addListener(IActorFutureListener<T> listener);
	
	public void removeListener(IActorFutureListener<T> listener);
}
