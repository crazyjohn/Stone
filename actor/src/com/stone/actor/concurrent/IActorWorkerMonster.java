package com.stone.actor.concurrent;

/**
 * I call this thread thing monster;
 * 
 * @author crazyjohn
 *
 */
public interface IActorWorkerMonster {

	public void submit(IActorRunnable iActorRunnable);

	public void startWorker();

	public void stopWorker();

}
