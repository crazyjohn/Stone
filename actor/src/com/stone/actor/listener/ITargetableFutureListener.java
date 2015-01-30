package com.stone.actor.listener;

import com.stone.actor.id.IActorId;

/**
 * 指定目标活动对象的listener;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface ITargetableFutureListener<T> extends IActorFutureListener<T> {
	/**
	 * 获取活动对象id;
	 * 
	 * @return
	 */
	public IActorId getTarget();
}
