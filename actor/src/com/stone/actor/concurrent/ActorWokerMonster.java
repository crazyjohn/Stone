package com.stone.actor.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * base worker monster;<br>
 * FIXME: crazyjohn maybe i should use java concurrent lib?
 * 
 * @author crazyjohn
 *
 */
public class ActorWokerMonster implements IActorWorkerMonster, Runnable {
	protected BlockingQueue<IActorRunnable> runnableQueue = new LinkedBlockingQueue<IActorRunnable>();
	protected volatile boolean stop = true;
	/** executor service */
	private ExecutorService executor;
	/** logger */
	private Logger logger = LoggerFactory.getLogger(ActorWokerMonster.class);

	public ActorWokerMonster(String monsterName) {
		executor = Executors.newSingleThreadExecutor(new NamedThreadFactory(monsterName));
	}

	@Override
	public void submit(IActorRunnable iActorRunnable) {
		try {
			runnableQueue.put(iActorRunnable);
		} catch (InterruptedException e) {
			logger.error("Handle thread interrupted", e);
		}
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				IActorRunnable runnable = this.runnableQueue.take();
				runnable.run();
			} catch (InterruptedException e) {
				// handle interrupt
				logger.info("Received interrupt command, try to break the main loop");
				handleLeftQueue();
				break;
			} catch (Exception e) {
				// record
				logger.error("Run ActorRun error", e);
			}
		}
	}

	/**
	 * handle left queue runnable;
	 */
	protected void handleLeftQueue() {
		// TODO: crazyjohn now do nothing?

	}

	@Override
	public void start() {
		if (!stop) {
			return;
		}
		stop = false;
		// start the thread
		this.executor.submit(this);
	}

	@Override
	public void shutdown() {
		stop = true;
		// send interrupt command
		this.executor.shutdownNow();
	}
}
