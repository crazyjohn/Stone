package com.stone.actor;

/**
 * Actor调度中心;<br>
 * 活动对象调度中心, 这里封装具体的线程逻辑, actor层感觉不到actor的存在;
 * 
 * @author crazyjohn
 *
 */
public interface IActorSystem {

	public void dispatch(IActorId actorId, IActorCallback callback);

	public void dispatch(IActorId actorId, IActorCall<?> call);

	public void start();

	public void stop();

}
