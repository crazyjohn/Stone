package com.stone.actor.system;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.IActor;
import com.stone.actor.annotation.GuardedByUnit;
import com.stone.actor.annotation.ThreadSafeUnit;
import com.stone.actor.call.IActorCall;
import com.stone.actor.call.IActorCallback;
import com.stone.actor.concurrent.ActorWokerMonster;
import com.stone.actor.concurrent.IActorRunnable;
import com.stone.actor.concurrent.IActorWorkerMonster;
import com.stone.actor.concurrent.NamedThreadFactory;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.ActorId;
import com.stone.actor.id.IActorId;

/**
 * base actor system;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public abstract class ActorSystem implements IActorSystem, Runnable {
	/** prefix */
	protected String systemPrefix = "ActorWokerMonster-";
	private static final long SLEEP_INTERVAL = 100L;
	/** hash index */
	protected Map<IActorId, IActor> actors = new ConcurrentHashMap<IActorId, IActor>();
	/** work monsters */
	protected IActorWorkerMonster[] workerMonsters;
	@GuardedByUnit(whoCareMe = "use volatile procted to mem sync")
	protected volatile boolean stop = true;
	private int workerNum;
	@GuardedByUnit(whoCareMe = "use class lock")
	/** log */
	private Logger logger = LoggerFactory.getLogger(ActorSystem.class);
	/** executor */
	private ExecutorService executor;
	/** idGenarator */
	private AtomicLong idCounter = new AtomicLong();
	/** system call */
	protected BlockingQueue<QueuedSytemCall<?>> systemCalls = new LinkedBlockingQueue<QueuedSytemCall<?>>();

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
		workerMonsters = new IActorWorkerMonster[threadNum];
		// executor
		executor = Executors.newSingleThreadExecutor(new NamedThreadFactory(systemPrefix + "main"));
		for (int i = 0; i < threadNum; i++) {
			workerMonsters[i] = createWorkerMonster(systemPrefix + i);
		}
		logger.info("Init the ActorSystem finished.");
	}

	/**
	 * Create a worker monster;
	 * <p>
	 * It works just like a WorkerMonster factory;
	 * 
	 * @return
	 */
	protected IActorWorkerMonster createWorkerMonster(String monsterName) {
		return new ActorWokerMonster(monsterName);
	}

	@Override
	public void dispatch(IActorId actorId, IActorCallback<?> callback, Object result) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.tell(callback, result);
	}

	@Override
	public void dispatch(IActorId actorId, IActorCall<?> call) {
		IActor actor = this.actors.get(actorId);
		if (actor == null) {
			return;
		}
		actor.ask(call);
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
					// handle interrupt?
					logger.info("Received interrupt command");
					break;
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
		return workerMonsters[workerIndex];
	}

	@Override
	public void start() {
		if (!stop) {
			return;
		}
		logger.info("Begin to start the ActorSystem...");
		stop = false;
		for (IActorWorkerMonster eachMonster : this.workerMonsters) {
			eachMonster.start();
		}
		// executor self
		executor.execute(this);
		logger.info("Start the ActorSystem finished.");
	}

	@Override
	public void stop() {
		// set flag
		stop = true;
		// stop main executorService
		this.executor.shutdownNow();
		// stop the monsters
		for (IActorWorkerMonster eachMonster : this.workerMonsters) {
			eachMonster.shutdown();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IActor> T getActor(IActorId actorId) {
		return (T) actors.get(actorId);
	}

	@Override
	public void registerActor(IActor actor) {
		// set actor id
		actor.setActorId(new ActorId(actor.getActorType(), this.idCounter.incrementAndGet()));
		this.actors.put(actor.getActorId(), actor);
		actor.setHostSystem(this);
	}

	@Override
	public <T> IActorFuture<T> putSystemCall(IActorSystemCall<T> call) {
		IActorFuture<T> systemFuture = new ActorFuture<T>();
		systemCalls.add(newQueuedSystemCall(call, systemFuture));
		return systemFuture;
	}

	/**
	 * 创建新的系统调用;<br>
	 * 子类可以覆盖实现自己的逻辑;
	 * 
	 * @param call
	 * @param systemFuture
	 * @return
	 */
	protected <T> QueuedSytemCall<T> newQueuedSystemCall(IActorSystemCall<T> call, IActorFuture<T> systemFuture) {
		return new QueuedSytemCall<T>(call, systemFuture);
	}

	/**
	 * the actor system call;
	 * 
	 * @author crazyjohn
	 *
	 * @param <T>
	 */
	public class QueuedSytemCall<T> {
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

		public void execute() {
			// do nothing
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
			actor.act();
		}

	}

}
