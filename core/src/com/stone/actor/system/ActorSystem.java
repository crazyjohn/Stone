package com.stone.actor.system;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.IActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.concurrent.ActorWokerMonster;
import com.stone.actor.concurrent.IActorRunnable;
import com.stone.actor.concurrent.IActorWorkerMonster;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.ActorId;
import com.stone.actor.id.IActorId;
import com.stone.core.annotation.GuardedByUnit;
import com.stone.core.annotation.ThreadSafeUnit;
import com.stone.core.concurrent.NamedThreadFactory;

/**
 * base actor system;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class ActorSystem implements IActorSystem, Runnable {
	/** prefix */
	private static final String MONSTER_PREFIX = "ActorWokerMonster-";
	private static final long SLEEP_INTERVAL = 100L;
	/** hash index */
	protected Map<IActorId, IActor> actors = new ConcurrentHashMap<IActorId, IActor>();
	/** work monsters */
	protected IActorWorkerMonster[] workerThreads;
	@GuardedByUnit(whoCareMe = "use volatile procted to mem sync")
	protected volatile boolean stop = true;
	private int workerNum;
	@GuardedByUnit(whoCareMe = "use class lock")
	private static IActorSystem instance = new ActorSystem();
	/** log */
	private Logger logger = LoggerFactory.getLogger(ActorSystem.class);
	/** executor */
	private Executor executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("ActorSystem"));
	/** idGenarator */
	private AtomicLong idCounter = new AtomicLong();
	/** system call */
	private BlockingQueue<QueuedSytemCall<?>> systemCalls = new LinkedBlockingQueue<QueuedSytemCall<?>>();

	/**
	 * private
	 */
	protected ActorSystem() {

	}

	/**
	 * 初始化ActorSystem;
	 * 
	 * @param threadNum
	 */
	@Override
	public void initSystem(int threadNum) {// init worker thread
		logger.info("Begin to init the ActorSystem...");
		workerNum = threadNum;
		workerThreads = new IActorWorkerMonster[threadNum];
		for (int i = 0; i < threadNum; i++) {
			workerThreads[i] = new ActorWokerMonster();
			workerThreads[i].setMonsterName(MONSTER_PREFIX + i);
		}
		logger.info("Init the ActorSystem finished.");
	}

	@Override
	public void dispatch(IActorId actorId, IActorCallback<?> callback, Object result) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.submit(callback, result);
	}

	@Override
	public void dispatch(IActorId actorId, IActorCall<?> call) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.submit(call);
	}

	@Override
	public void run() {
		while (!stop) {
			// handle system call
			boolean noSystemCall = handleSystemCall();
			// handle actor run
			boolean noActorRun = handleActorRun();
			// have a rest when i have no work to do
			if (noSystemCall && noActorRun) {
				try {
					Thread.sleep(SLEEP_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * handle actor run;
	 * 
	 * @return
	 */
	private boolean handleActorRun() {
		boolean noActorRun = true;
		for (final Map.Entry<IActorId, IActor> eachActorEntry : this.actors.entrySet()) {
			final IActor actor = eachActorEntry.getValue();
			if (!actor.hasAnyWorkToDo()) {
				continue;
			}
			noActorRun = false;
			// get work thread
			IActorWorkerMonster workThread = getActorWorkerMonster(eachActorEntry.getKey());
			if (workThread != null) {
				workThread.submit(new ActorRunnable(actor));
			} else {
				// log
				logger.warn(String.format("Can not find WorkerMonster for this actor: %s", eachActorEntry.getKey()));
			}
		}
		return noActorRun;

	}

	/**
	 * 处理系统调用, 子ActorSystem自己去实现自己的处理逻辑;
	 * 
	 * @return
	 */
	protected boolean handleSystemCall() {
		return true;
	}

	/**
	 * get the WorkerMonster;
	 * 
	 * @param actorId
	 * @return
	 */
	private IActorWorkerMonster getActorWorkerMonster(IActorId actorId) {
		int workerIndex = actorId.getWorkerMonsterIndex(this.workerNum);
		return workerThreads[workerIndex];
	}

	@Override
	public void start() {
		if (!stop) {
			return;
		}
		logger.info("Begin to start the ActorSystem...");
		stop = false;
		for (IActorWorkerMonster eachMonster : this.workerThreads) {
			eachMonster.startWorker();
		}
		// executor self
		executor.execute(this);
		logger.info("Start the ActorSystem finished.");
	}

	@Override
	public void stop() {
		stop = true;
	}

	public static synchronized IActorSystem getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IActor> T getActor(IActorId actorId) {
		return (T) actors.get(actorId);
	}

	@Override
	public void registerActor(IActor actor) {
		// set actor id
		actor.setActorId(new ActorId(actor.getActorId().getActorType(), this.idCounter.incrementAndGet()));
		this.actors.put(actor.getActorId(), actor);
	}

	@Override
	public <T> IActorFuture<T> putSystemCall(IActorSystemCall<T> call) {
		IActorFuture<T> sytemFuture = new ActorFuture<T>();
		systemCalls.add(new QueuedSytemCall<>(call, sytemFuture));
		return sytemFuture;
	}

	/**
	 * the actor system call;
	 * 
	 * @author crazyjohn
	 *
	 * @param <T>
	 */
	protected class QueuedSytemCall<T> {
		private IActorSystemCall<T> systemCall;
		private IActorFuture<T> future;

		public IActorSystemCall<T> getSystemCall() {
			return systemCall;
		}

		public IActorFuture<T> getFuture() {
			return future;
		}

		public QueuedSytemCall(IActorSystemCall<T> systemCall, IActorFuture<T> future) {
			this.systemCall = systemCall;
			this.future = future;
		}
	}

	/**
	 * actor runnable;
	 * 
	 * @author crazyjohn
	 *
	 */
	class ActorRunnable implements IActorRunnable {
		private IActor actor;

		public ActorRunnable(IActor actor) {
			this.actor = actor;
		}

		@Override
		public void run() {
			actor.run();
		}

	}

}
