package com.stone.actor.call;

/**
 * Actor调用接口;
 * 
 * @author crazyjohn
 *
 */
public interface IActorCall<Result> {

	/**
	 * execute me;
	 * 
	 * @return
	 */
	public Result execute();
}
