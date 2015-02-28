package com.stone.actor.system;

import com.stone.actor.data.IActorDBService;

/**
 * The actor system call;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface IActorSystemCall<T> {

	public T execute(IActorDBService dbService);

	public IActorSystem getCallerSystem();

	public long getCallerActorId();

}
