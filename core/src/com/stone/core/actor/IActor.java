package com.stone.core.actor;

/**
 * Actor接口;
 * 
 * @author crazyjohn
 *
 */
public interface IActor {

	public void put(IActorCall call);

	public void put(IActorCall call, IActorCallback callback, IActorId source);

	public void run();

}
