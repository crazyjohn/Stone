package com.stone.actor.call;

import com.stone.actor.IActorId;

/**
 * Actor执行回调;
 * 
 * @author crazyjohn
 *
 */
public interface IActorCallback {

	/**
	 * do callback;
	 */
	public void doCallback();

	/**
	 * who execute me?
	 * 
	 * @param source
	 */
	public void setTarget(IActorId source);

	public IActorId getTarget();
}
