package com.stone.actor.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.stone.actor.IActor;
import com.stone.actor.IActorCall;
import com.stone.actor.IActorCallback;
import com.stone.actor.IActorId;
import com.stone.actor.IActorSystem;

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
