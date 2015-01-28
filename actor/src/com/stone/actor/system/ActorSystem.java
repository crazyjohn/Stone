package com.stone.actor.system;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stone.actor.IActor;
import com.stone.actor.IActorId;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.concurrent.ActorWokerThread;
import com.stone.actor.concurrent.IActorRunnable;
import com.stone.actor.concurrent.IActorWorkerThread;

public class ActorSystem implements IActorSystem, Runnable {
	/** hash index */
	protected Map<IActorId, IActor> actors = new ConcurrentHashMap<IActorId, IActor>();
	protected IActorWorkerThread[] workerThreads;
	protected volatile boolean stop = true;
	private int workerNum;

	public ActorSystem(int threadNum) {
		// init worker thread
		workerNum = threadNum;
		workerThreads = new IActorWorkerThread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			workerThreads[i] = new ActorWokerThread();
		}
	}

	@Override
	public void dispatch(IActorId actorId, IActorCallback callback) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.put(callback);
	}

	@Override
	public void dispatch(IActorId actorId, IActorCall<?> call) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.call(call);
	}

	@Override
	public void run() {
		while (!stop) {
			for (final Map.Entry<IActorId, IActor> eachActorEntry : this.actors.entrySet()) {
				IActorWorkerThread workThread = getActorWorkerThread(eachActorEntry.getKey());
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

	private IActorWorkerThread getActorWorkerThread(IActorId actorId) {
		int workerIndex = actorId.getWorkerThreadIndex(this.workerNum);
		return this.workerThreads[workerIndex];
	}

	@Override
	public void start() {
		stop = false;
	}

	@Override
	public void stop() {
		stop = true;
	}

}
