package com.stone.actor;

public interface IActorFuture<T> {

	public T getResult();

	public void setResult(T result);

	public boolean isReady();

	public void ready();
}
