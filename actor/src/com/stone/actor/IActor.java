package com.stone.actor;

import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.call.IActorNetCall;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.system.IActorSystem;

/**
 * Actor;
 * <p>
 * you know, no thread and lock and other fucking concurrent things, just actor
 * model;
 * 
 * @author crazyjohn
 *
 */
public interface IActor {
	/**
	 * Use the ask way, you will get a future return;
	 * <p>
	 * Just follow the AKKA way;
	 * 
	 * @param call
	 * @return
	 */
	public <T> IActorFuture<T> ask(IActorCall<T> call);

	/**
	 * Use tell way to submit a call;
	 * 
	 * @param call
	 */
	public <T> void tell(IActorCall<T> call);

	/**
	 * submit a net message;
	 * <p>
	 * In this way, you will get nothing return. Just follow the AKKA way;
	 * 
	 * @param message
	 */
	public void tell(IActorNetCall message);

	/**
	 * submit a simple callback and result to actor;
	 * 
	 * @param callback
	 * @param result
	 */
	public void tell(IActorCallback<?> callback, Object result);

	/**
	 * the actor's main loop;
	 */
	public void act();

//	/**
//	 * You need to handle the received message in this method;
//	 * 
//	 * @param message
//	 */
//	public void onReceive(Object message);

	/**
	 * get my actorId;
	 * 
	 * @return
	 */
	public long getActorId();

	/**
	 * set actor id;
	 * 
	 * @param id
	 */
	public void setActorId(long id);

	/**
	 * has any work to do?
	 * 
	 * @return
	 */
	public boolean hasAnyWorkToDo();

	/**
	 * get host actor system;
	 * 
	 * @return
	 */
	public IActorSystem getHostSystem();

	/**
	 * set host actor system;
	 * 
	 * @param actorSystem
	 */
	public void setHostSystem(IActorSystem actorSystem);

}
