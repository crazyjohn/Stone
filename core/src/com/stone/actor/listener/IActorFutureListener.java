package com.stone.actor.listener;

import com.stone.actor.IActor;
import com.stone.actor.future.IActorFuture;
import com.stone.core.annotation.NotThreadSafeUnit;

/**
 * future监听器;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface IActorFutureListener<T> {

	/**
	 * future计算完成后的回调;<br>
	 * 非线程安全, 所以要注意回调内部不要操作上下文;
	 * 
	 * @param future
	 */
	@NotThreadSafeUnit(desc = "do not operate the host context")
	public void onComplete(IActorFuture<T> future);

	/**
	 * 获取活动对象;
	 * 
	 * @return
	 */
	public IActor getTarget();

}
