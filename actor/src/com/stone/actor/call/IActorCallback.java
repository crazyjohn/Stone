package com.stone.actor.call;

/**
 * Actor执行回调;
 * 
 * @author crazyjohn
 *
 */
public interface IActorCallback<Result> {

	/**
	 * do callback;
	 */
	public void doCallback(Result result);

}
