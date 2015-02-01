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
import com.stone.actor.id.ActorId;
import com.stone.actor.id.ActorType;
import com.stone.actor.id.IActorId;
import com.stone.actor.system.IActorSystem;

/**
 * 基础的Actor实现;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseActor implements IActor {
	/** blocking queue call */
	protected BlockingQueue<IActorQueueCall> callQueue = new LinkedBlockingQueue<IActorQueueCall>();
	private volatile boolean stop = true;
	protected IActorSystem actorSystem;
	private Logger logger = LoggerFactory.getLogger(BaseActor.class);
	protected IActorId actorId;

	public BaseActor(ActorType actorType, long id) {
		this.actorId = new ActorId(actorType, id);
	}

	@Override
	public boolean hasAnyWorkToDo() {
		return !callQueue.isEmpty();
	}

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
	public <T> IActorFuture<T> put(IActorCall<T> call) {
		IActorFuture<T> future = new ActorFuture<T>();
		callQueue.add(new QueueCallWithFuture(call, future));
		return future;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void put(IActorCallback<?> callback, Object result) {
		callQueue.add(new QueueOnlyCallback(callback, result));
	}

	@Override
	public void put(IActorCall<?> call, IActorCallback<?> callback,
			IActorId source) {
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

	/**
	 * 队列调用接口;
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IActorQueueCall {

		/**
		 * 获取调用;
		 * 
		 * @return
		 */
		public IActorCall<?> getCall();

		/**
		 * 执行;
		 */
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

	/**
	 * 只有回调的调用;
	 * 
	 * @author crazyjohn
	 *
	 * @param <T>
	 */
	class QueueOnlyCallback<T> implements IActorQueueCall {
		private IActorCallback<T> callback;
		private T result;

		public QueueOnlyCallback(IActorCallback<T> callback, T result) {
			this.callback = callback;
			this.result = result;
		}

		@Override
		public IActorCall<?> getCall() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void execute() {
			this.callback.doCallback(result);
		}

	}

	/**
	 * 带回调的调用;
	 * 
	 * @author crazyjohn
	 *
	 */
	class QueueCallWithCallback extends BaseQueueCall {
		private IActorCallback<?> callback;
		private IActorId target;

		public QueueCallWithCallback(IActorCall<?> call,
				IActorCallback<?> callback, IActorId target) {
			super(call);
			this.callback = callback;
			this.target = target;
		}

		@Override
		public void execute() {
			IActorCall<?> call = this.getCall();
			Object result = call.execute();
			IActorCallback<?> callback = this.callback;
			// 回调投递给actorSystem
			actorSystem.dispatch(target, callback, result);
		}

	}

	/**
	 * 带Future的队列调用;
	 * 
	 * @author crazyjohn
	 *
	 */
	class QueueCallWithFuture extends BaseQueueCall {
		private IActorFuture<?> future;

		public QueueCallWithFuture(IActorCall<?> call, IActorFuture<?> future) {
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
