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
	 * 返回执行结果(Balking pattern);<br>
	 * 不会阻塞，如果结果没有执行完成，则会立即返回；
	 * 
	 * @return 可能会返回null，此种情况发生在结果没有执行完成的情况下;
	 */
	public T getResult();

	/**
	 * 阻塞等待结果(Guarder suspension pattern);
	 * 
	 * @return
	 * @throws InterruptedException
	 *             可中断, 中断抛出此异常;
	 */
	public T awaitResult() throws InterruptedException;

	/**
	 * 带超时的阻塞等待结果;
	 * 
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public T awaitResult(long timeout) throws InterruptedException;

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
