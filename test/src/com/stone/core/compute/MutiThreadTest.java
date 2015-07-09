package com.stone.core.compute;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mutiple thread test;
 * 
 * @author crazyjohn
 *
 */
public class MutiThreadTest {
	/** The request count */
	static int requestCount = 10000;
	/** default item id */
	protected static final int DEFAULT_ITEM_ID = 8888;
	/** default bag size */
	protected static final int DEFAULT_BAG_SIZE = 10000;
	/** the player's item bag */
	static List<Integer> bag = new ArrayList<Integer>();

	/**
	 * Generate the bag;
	 */
	private static void genarateBag() {
		for (int i = 0; i < requestCount; i++) {
			bag.add(i);
		}
	}

	/**
	 * Handle the messages;
	 * 
	 * @param threadCount
	 * @throws InterruptedException
	 */
	private static void handleMessages(int threadCount) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		final CountDownLatch latch = new CountDownLatch(requestCount);
		for (int i = 0; i < requestCount; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					boolean result = hasSuchItem(DEFAULT_ITEM_ID);
					if (result) {
						latch.countDown();
					}
				}

			});
		}
		// wait
		latch.await();
		long costTime = System.currentTimeMillis() - startTime;
		System.out.println(String.format("Use thread count: %d, cost times: %dms", threadCount, costTime));
		// shutdown
		executor.shutdown();
	}

	/**
	 * Is the player has such item?
	 * 
	 * @param itemId
	 * @return
	 */
	protected static boolean hasSuchItem(int itemId) {
		for (int i = 0; i < requestCount; i++) {
			if (bag.get(i) == itemId) {
				return true;
			}

		}
		return false;
	}

	public static void main(String[] args) throws InterruptedException {
		// generate bag
		genarateBag();
		// handle msg
		// fucking codes
		// int threadCount = 1;
		// int addCount = 5;
		// while (threadCount < 1000) {
		// threadCount += addCount;
		// handleMessages(threadCount);
		// }
		handleMessages(1);
		handleMessages(5);
		handleMessages(10);
		handleMessages(50);
		handleMessages(100);
		handleMessages(500);
		handleMessages(1000);

	}
}
