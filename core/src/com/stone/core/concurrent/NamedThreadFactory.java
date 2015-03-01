package com.stone.core.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Named thread factory;
 * <p>
 * From jdk concurrent lib;
 * 
 * @author crazyjohn
 *
 */
public class NamedThreadFactory implements ThreadFactory {
	// private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	protected static AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	public NamedThreadFactory(String name) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = name;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix, 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
