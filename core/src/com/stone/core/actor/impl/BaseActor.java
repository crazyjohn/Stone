package com.stone.core.actor.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.actor.IActor;
import com.stone.core.actor.IActorCall;
import com.stone.core.actor.IActorCallback;
import com.stone.core.actor.IActorId;
import com.stone.core.actor.IActorSystem;

public abstract class BaseActor implements IActor {
	protected BlockingQueue<IActorCall> calls = new LinkedBlockingQueue<>();
	protected Map<IActorCall, IActorCallback> callbacks = new ConcurrentHashMap<IActorCall, IActorCallback>();
	private volatile boolean stop = true;
	protected IActorSystem actorSystem;
	private Logger logger = LoggerFactory.getLogger(BaseActor.class);

	@Override
	public void start() {
		this.stop = false;
	}

	@Override
	public void stop() {
		this.stop = true;
	}

	@Override
	public void put(IActorCall call) {
		calls.add(call);
	}

	@Override
	public void put(IActorCall call, IActorCallback callback, IActorId source) {
		callback.setTarget(source);
		callbacks.put(call, callback);
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				Iterator<IActorCall> iterator = this.calls.iterator();
				while (iterator.hasNext()) {
					IActorCall call = iterator.next();
					call.execute();
					if (callbacks.get(call) != null) {
						actorSystem.dispatch(callbacks.get(call), callbacks.get(call).getTarget());
					}
					iterator.remove();
				}
			} catch (Exception e) {
				logger.error("Execute call error", e);
			}
		}
	}

}
