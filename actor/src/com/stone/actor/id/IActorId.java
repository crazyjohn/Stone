package com.stone.actor.id;

/**
 * The actor id;
 * 
 * @author crazyjohn
 *
 */
public interface IActorId {

	/**
	 * get worker monster index;
	 * 
	 * @param workerNum
	 * @return
	 */
	public int getWorkerMonsterIndex(int workerNum);

	/**
	 * get the ActorType;
	 * 
	 * @return
	 */
	public ActorType getActorType();

	/**
	 * get the actor long id;
	 * 
	 * @return
	 */
	public long getId();

}
