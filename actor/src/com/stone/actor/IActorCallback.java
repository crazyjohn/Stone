package com.stone.actor;

/**
 * Actor执行回调;
 * 
 * @author crazyjohn
 *
 */
public interface IActorCallback {

	public void doCallback();

	public void setTarget(IActorId source);

	public IActorId getTarget();
}
