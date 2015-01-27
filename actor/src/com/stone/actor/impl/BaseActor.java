package com.stone.actor.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.IActor;
import com.stone.actor.IActorCall;
import com.stone.actor.IActorCallback;
import com.stone.actor.IActorId;
import com.stone.actor.IActorSystem;

public abstract class BaseActor implements IActor {
	protected BlockingQueue<IActorCall> callQueue = new LinkedBlockingQueue<>();
	protected BlockingQueue<IActorCallback> callbackQueue = new LinkedBlockingQueue<>();
	protected Map<IActorCall, IActorCallback> registerCallbacks = new ConcurrentHashMap<IActorCall, IActorCallback>();
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
		callQueue.add(call);
	}

	@Override
	public void put(IActorCallback callback) {
		callbackQueue.add(callback);
	}

	@Override
	public void put(IActorCall call, IActorCallback callback, IActorId source) {
		callback.setTarget(source);
		registerCallbacks.put(call, callback);
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				Iterator<IActorCall> iterator = this.callQueue.iterator();
				while (iterator.hasNext()) {
					IActorCall call = iterator.next();
					call.execute();
					if (registerCallbacks.get(call) != null) {
						actorSystem.dispatch(registerCallbacks.get(call).getTarget(), registerCallbacks.get(call));
					}
					iterator.remove();
				}
			} catch (Exception e) {
				logger.error("Execute call error", e);
			}
		}
	}

}
