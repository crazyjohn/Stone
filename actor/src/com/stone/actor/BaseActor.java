package com.stone.actor;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.IActorId;
import com.stone.actor.system.IActorSystem;

public abstract class BaseActor implements IActor {
	protected BlockingQueue<IActorQueueCall> callQueue = new LinkedBlockingQueue<IActorQueueCall>();
	protected BlockingQueue<IActorCallback<?>> callbackQueue = new LinkedBlockingQueue<IActorCallback<?>>();
	private volatile boolean stop = true;
	protected IActorSystem actorSystem;
	private Logger logger = LoggerFactory.getLogger(BaseActor.class);
	protected IActorId actorId;

	@Override
	public void start() {
		this.stop = false;
	}

	@Override
	public IActorId getActorId() {
		return actorId;
	}

	@Override
	public void stop() {
		this.stop = true;
	}

	@Override
	public <T> IActorFuture<T> call(IActorCall<T> call) {
		IActorFuture<T> future = new ActorFuture<T>();
		callQueue.add(new QueueCall(call, future));
		return future;
	}

	@Override
	public void put(IActorCallback<?> callback) {
		callbackQueue.add(callback);
	}

	@Override
	public void put(IActorCall<?> call, IActorCallback<?> callback, IActorId source) {
		this.callQueue.add(new QueueCallWithCallback(call, callback, source));
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				Iterator<IActorQueueCall> iterator = this.callQueue.iterator();
				while (iterator.hasNext()) {
					IActorQueueCall queueCall = iterator.next();
					queueCall.execute();
					iterator.remove();
				}
			} catch (Exception e) {
				logger.error("Execute call error", e);
			}
		}
	}

	interface IActorQueueCall {

		public IActorCall<?> getCall();

		public void execute();
	}

	abstract class BaseQueueCall implements IActorQueueCall {
		protected IActorCall<?> call;

		public BaseQueueCall(IActorCall<?> call) {
			this.call = call;
		}

		@Override
		public IActorCall<?> getCall() {
			return call;
		}

	}

	class QueueCallWithCallback extends BaseQueueCall {
		private IActorCallback<?> callback;
		private IActorId target;

		public QueueCallWithCallback(IActorCall<?> call, IActorCallback<?> callback, IActorId target) {
			super(call);
			this.callback = callback;
			this.target = target;
		}

		@Override
		public void execute() {
			IActorCall<?> call = this.getCall();
			Object result = call.execute();
			IActorCallback<?> callback = this.callback;
			actorSystem.dispatch(target, callback, result);
		}

	}

	class QueueCall extends BaseQueueCall {
		private IActorFuture<?> future;

		public QueueCall(IActorCall<?> call, IActorFuture<?> future) {
			super(call);
			this.future = future;
		}

		public IActorFuture<?> getFuture() {
			return future;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void execute() {
			IActorCall<?> call = this.getCall();
			@SuppressWarnings("rawtypes")
			IActorFuture future = this.getFuture();
			future.setResult(call.execute());
		}

	}

}
