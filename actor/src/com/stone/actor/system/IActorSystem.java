package com.stone.actor.system;

import com.stone.actor.IActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.id.IActorId;

/**
 * Actor调度中心;<br>
 * 活动对象调度中心, 这里封装具体的线程逻辑, actor层感觉不到actor的存在;
 * 
 * @author crazyjohn
 *
 */
public interface IActorSystem {

	/**
	 * 分发调用到指定的Actor;
	 * 
	 * @param actorId
	 *            活动对象Id;
	 * @param callback
	 *            回调对象;
	 * @param result
	 *            执行结果;
	 */
	public void dispatch(IActorId actorId, IActorCallback<?> callback,
			Object result);

	/**
	 * 分发回调到指定的Actor;
	 * 
	 * @param actorId
	 * @param call
	 */
	public void dispatch(IActorId actorId, IActorCall<?> call);

	/**
	 * 启动Actor系统;
	 */
	public void start();

	/**
	 * 停止Actor系统;
	 */
	public void stop();

	/**
	 * 获取玩家Actor;
	 * 
	 * @param playerId
	 * @return
	 */
	public <T extends IActor>T getPlayerActor(long playerId);

	/**
	 * 初始化ActorSystem;
	 * 
	 * @param threadNum
	 */
	public void initSystem(int threadNum);

	/**
	 * regist an actor;
	 * 
	 * @param player
	 */
	public void registerActor(IActor actor);

}
