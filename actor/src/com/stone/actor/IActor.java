package com.stone.actor;

import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.IActorId;
import com.stone.core.msg.IMessage;

/**
 * Actor接口;
 * 
 * @author crazyjohn
 *
 */
public interface IActor {
	/**
	 * 投递一个调用给Actor;
	 * 
	 * @param call
	 * @return
	 */
	public <T> IActorFuture<T> put(IActorCall<T> call);

	/**
	 * 投递回调;
	 * 
	 * @param callback
	 *            回调对象;
	 * @param result
	 *            结果参数;
	 */
	public void put(IActorCallback<?> callback, Object result);

	/**
	 * 投递一个调用以及一个回调, 以及处理回调的ActorId;
	 * 
	 * @param call
	 * @param callback
	 * @param source
	 */
	public void put(IActorCall<?> call, IActorCallback<?> callback, IActorId source);

	/**
	 * the run method;
	 */
	public void run();

	/**
	 * 启动actor;
	 */
	public void start();

	/**
	 * 停止actor;
	 */
	public void stop();

	/**
	 * 获取ActorId;
	 * 
	 * @return
	 */
	public IActorId getActorId();

	/**
	 * set actor id;
	 * 
	 * @param id
	 */
	public void setActorId(IActorId id);

	/**
	 * has any work to do?
	 * 
	 * @return
	 */
	public boolean hasAnyWorkToDo();

	/**
	 * put net message;
	 * 
	 * @param message
	 */
	public void put(IMessage message);

}
