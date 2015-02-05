package com.stone.actor.concurrent;

import java.util.concurrent.BlockingQueue;
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
public class ActorWokerMonster extends Thread implements IActorWorkerMonster {
	protected BlockingQueue<IActorRunnable> runnableQueue = new LinkedBlockingQueue<IActorRunnable>();
	protected volatile boolean stop = true;
	/** logger */
	private Logger logger = LoggerFactory.getLogger(ActorWokerMonster.class);

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
				logger.error("Received interrupt command, try to break the main loop", e);
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
	public void startWorker() {
		if (!stop) {
			return;
		}
		stop = false;
		this.start();
	}

	@Override
	public void stopWorker() {
		stop = true;
		// send interrupt command
		this.interrupt();
	}

	@Override
	public void setMonsterName(String monsterName) {
		this.setName(monsterName);
	}

}
