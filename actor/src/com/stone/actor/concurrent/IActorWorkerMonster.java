package com.stone.actor.concurrent;

/**
 * I call this thread thing monster;
 * 
 * @author crazyjohn
 *
 */
public interface IActorWorkerMonster {

	/**
	 * submit a actor runnable;
	 * 
	 * @param iActorRunnable
	 */
	public void submit(IActorRunnable iActorRunnable);

	/**
	 * start the monter;
	 */
	public void start();

	/**
	 * stop the monster;
	 */
	public void shutdown();

}
