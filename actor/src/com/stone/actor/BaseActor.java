package com.stone.actor;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.call.IActorNetCall;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
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
	protected BlockingQueue<IActorQueueExecutable> callQueue = new LinkedBlockingQueue<IActorQueueExecutable>();
	/** host system */
	protected IActorSystem actorSystem;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(BaseActor.class);
	/** actor id */
	protected IActorId actorId;

	@Override
	public void setActorId(IActorId id) {
		this.actorId = id;
	}

	@Override
	public boolean hasAnyWorkToDo() {
		return !callQueue.isEmpty();
	}

	@Override
	public IActorId getActorId() {
		return actorId;
	}

	@Override
	public <T> IActorFuture<T> ask(IActorCall<T> call) {
		IActorFuture<T> future = new ActorFuture<T>();
		callQueue.add(new QueueCallWithFuture(call, future));
		return future;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void tell(IActorCallback<?> callback, Object result) {
		callQueue.add(new QueueOnlyCallback(callback, result));
	}

	@Override
	public <T> void tell(IActorCall<T> call) {
		callQueue.add(new QueueOnlyCall(call));
	}

	/**
	 * 投递一个调用以及一个执行回调, 以及执行回调的Actor;
	 * 
	 * @param call
	 * @param callback
	 * @param source
	 */
	protected void submit(IActorCall<?> call, IActorCallback<?> callback, IActorId source) {
		this.callQueue.add(new QueueCallWithCallback(call, callback, source));
	}

	@Override
	public void tell(IActorNetCall message) {
		this.callQueue.add(new QueueNetMessage(message));
	}

	@Override
	public void act() {
		Iterator<IActorQueueExecutable> iterator = this.callQueue.iterator();
		while (iterator.hasNext()) {
			IActorQueueExecutable queueCall = iterator.next();
			// first remove
			iterator.remove();
			// then execute
			queueCall.execute();
		}
	}

	@Override
	public IActorSystem getHostSystem() {
		return actorSystem;
	}

	@Override
	public void setHostSystem(IActorSystem actorSystem) {
		this.actorSystem = actorSystem;
	}

	/**
	 * queue executable;
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IActorQueueExecutable {
		/**
		 * 执行;
		 */
		public void execute();
	}

	/**
	 * 队列调用接口;
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IActorQueueCall extends IActorQueueExecutable {

		/**
		 * 获取调用;
		 * 
		 * @return
		 */
		public IActorCall<?> getCall();

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
	 * queue net message;
	 * 
	 * @author crazyjohn
	 *
	 */
	class QueueNetMessage implements IActorQueueExecutable {
		private IActorNetCall msg;

		public QueueNetMessage(IActorNetCall msg) {
			this.msg = msg;
		}

		@Override
		public void execute() {
			try {
				msg.execute();
			} catch (Exception e) {
				logger.error("Execute message error", e);
			}
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

	class QueueOnlyCall extends BaseQueueCall {

		public QueueOnlyCall(IActorCall<?> call) {
			super(call);
		}

		@Override
		public void execute() {
			this.call.execute();
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
