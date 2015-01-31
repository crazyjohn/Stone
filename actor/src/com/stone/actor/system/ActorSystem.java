package com.stone.actor.system;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stone.actor.IActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.concurrent.ActorWokerMonster;
import com.stone.actor.concurrent.IActorRunnable;
import com.stone.actor.concurrent.IActorWorkerMonster;
import com.stone.actor.id.IActorId;
import com.stone.actor.player.PlayerActor;
import com.stone.core.annotation.GuardedByUnit;
import com.stone.core.annotation.ThreadSafeUnit;

/**
 * 基础的ActorSystem实现;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class ActorSystem implements IActorSystem, Runnable {
	/** hash index */
	protected Map<IActorId, IActor> actors = new ConcurrentHashMap<IActorId, IActor>();
	protected IActorWorkerMonster[] workerThreads;
	@GuardedByUnit(whoCareMe = "use volatile procted to mem sync")
	protected volatile boolean stop = true;
	private int workerNum;
	private static IActorSystem instance = new ActorSystem();

	/**
	 * private
	 */
	private ActorSystem() {

	}

	/**
	 * 初始化ActorSystem;
	 * 
	 * @param threadNum
	 */
	public void initSystem(int threadNum) {// init worker thread
		workerNum = threadNum;
		workerThreads = new IActorWorkerMonster[threadNum];
		for (int i = 0; i < threadNum; i++) {
			workerThreads[i] = new ActorWokerMonster();
		}
	}

	@Override
	public void dispatch(IActorId actorId, IActorCallback<?> callback, Object result) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.put(callback, result);
	}

	@Override
	public void dispatch(IActorId actorId, IActorCall<?> call) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.put(call);
	}

	@Override
	public void run() {
		while (!stop) {
			for (final Map.Entry<IActorId, IActor> eachActorEntry : this.actors.entrySet()) {
				IActorWorkerMonster workThread = getActorWorkerMonster(eachActorEntry.getKey());
				if (workThread != null) {
					workThread.submit(new IActorRunnable() {
						@Override
						public void run() {
							eachActorEntry.getValue().run();
						}

					});
				} else {
					// FIXME: crazyjohn log
				}
			}
		}
	}

	private IActorWorkerMonster getActorWorkerMonster(IActorId actorId) {
		int workerIndex = actorId.getWorkerMonsterIndex(this.workerNum);
		return workerThreads[workerIndex];
	}

	@Override
	public void start() {
		stop = false;
	}

	@Override
	public void stop() {
		stop = true;
	}

	public static IActorSystem getInstance() {
		return instance;
	}

	@Override
	public PlayerActor getPlayerActor(long playerId) {
		return (PlayerActor) actors.get(playerId);
	}

}
