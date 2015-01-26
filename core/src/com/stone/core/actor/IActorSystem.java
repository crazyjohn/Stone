package com.stone.core.actor;

/**
 * Actor调度中心;
 * 
 * @author crazyjohn
 *
 */
public interface IActorSystem {

	public IActor dispatch(IActorCallback call, IActorId actorId);

}
