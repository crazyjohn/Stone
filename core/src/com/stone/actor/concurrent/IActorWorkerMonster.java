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
	public void startWorker();

	/**
	 * stop the monster;
	 */
	public void stopWorker();

	/**
	 * set a good monster name, then you can use for debug;
	 * 
	 * @param monsterName
	 */
	public void setMonsterName(String monsterName);

}
