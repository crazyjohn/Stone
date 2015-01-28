package com.stone.actor.future;

import com.stone.actor.listener.IActorFutureListener;

/**
 * Actor's future pattern;
 * 
 * @author crazyjohn
 * 
 * @param <T>
 */
public interface IActorFuture<T> {

	/**
	 * 返回执行结果;
	 * 
	 * @return
	 */
	public T getResult();

	/**
	 * 设置结果;
	 * 
	 * @param result
	 */
	public void setResult(T result);

	/**
	 * 是否已经设置好了结果;
	 * 
	 * @return
	 */
	public boolean isReady();

	/**
	 * 触发准备好;
	 */
	public void ready();

	/**
	 * 添加关心future结果的监听器;
	 * 
	 * @param listener
	 */
	public void addListener(IActorFutureListener<T> listener);

	/**
	 * 移除监听器;
	 * 
	 * @param listener
	 */
	public void removeListener(IActorFutureListener<T> listener);
}
