package com.stone.actor.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorWokerMonster extends Thread implements IActorWorkerMonster {
	protected BlockingQueue<IActorRunnable> runnableQueue = new LinkedBlockingQueue<IActorRunnable>();
	protected volatile boolean stop = true;

	@Override
	public void submit(IActorRunnable iActorRunnable) {
		try {
			runnableQueue.put(iActorRunnable);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				IActorRunnable runnable = this.runnableQueue.take();
				runnable.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// FIXME: crazyjohn log
			}
		}
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
	}

	@Override
	public void setMonsterName(String monsterName) {
		this.setName(monsterName);
	}

}
