package com.stone.core.actor.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.stone.core.actor.IActor;
import com.stone.core.actor.IActorCall;
import com.stone.core.actor.IActorCallback;
import com.stone.core.actor.IActorId;
import com.stone.core.actor.IActorSystem;

public class ActorSystem implements IActorSystem {
	protected Map<IActorId, IActor> actors = new ConcurrentHashMap<IActorId, IActor>();
	protected Executor executor;

	public ActorSystem(int threadNum) {
		this.executor = Executors.newFixedThreadPool(threadNum);
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
	public void dispatch(IActorId actorId, IActorCall call) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.put(call);
	}

}
