package com.stone.core.actor.impl;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.actor.IActor;
import com.stone.core.actor.IActorCall;
import com.stone.core.actor.IActorCallback;
import com.stone.core.actor.IActorId;

public class BaseActor implements IActor {
	protected BlockingQueue<IActorCall> calls = new LinkedBlockingQueue<>();
	private volatile boolean stop;
	private Logger logger = LoggerFactory.getLogger(BaseActor.class);

	@Override
	public void put(IActorCall call) {
		calls.add(call);
	}

	@Override
	public void put(IActorCall call, IActorCallback callback, IActorId source) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while (!stop) {
			try {
				Iterator<IActorCall> iterator = this.calls.iterator();
				while (iterator.hasNext()) {
					IActorCall call = iterator.next();
					call.execute();
					iterator.remove();
				}
			} catch (Exception e) {
				logger.error("Execute call error", e);
			}
		}
	}

}
