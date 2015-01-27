package com.stone.actor.concurrent;

public interface IActorWorkerThread {

	public void submit(IActorRunnable iActorRunnable);
	
	public void startWorker();
	
	public void stopWorker();

}
