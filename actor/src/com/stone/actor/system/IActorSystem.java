package com.stone.actor.system;

import com.stone.actor.IActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.future.IActorFuture;

/**
 * Actor System;
 * <p>
 * The actor schedule and manage center, the actor thread and lock logic be
 * hided in there, the actor layer or the app layer did not know the thread
 * shit;
 * 
 * @author crazyjohn
 *
 */
public interface IActorSystem {

	/**
	 * Dispatch an actor callback and result to an actor;
	 * 
	 * @param actorId
	 * @param callback
	 * @param result
	 */
	public void dispatch(long actorId, IActorCallback<?> callback, Object result);

	/**
	 * Dispatch an actor call to an actor;
	 * 
	 * @param actorId
	 * @param call
	 */
	public void dispatch(long actorId, IActorCall<?> call);

	/**
	 * Start the actor system;
	 */
	public void start();

	/**
	 * Stop the actor system;
	 */
	public void stop();

	/**
	 * get actor by actorId
	 * 
	 * @param actorId
	 * @return
	 */
	public <T extends IActor> T getActor(long actorId);

	/**
	 * Init the actor system;
	 * 
	 * @param workerNum
	 */
	public void initSystem(int workerNum);

	/**
	 * regist an actor;
	 * 
	 * @param player
	 */
	public void registerActor(IActor actor);

	/**
	 * Put an actory system call between two actor system;
	 * 
	 * @param call
	 * @return
	 */
	public <T> IActorFuture<T> putSystemCall(IActorSystemCall<T> call);

}
