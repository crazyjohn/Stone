package com.stone.test.concurrent;

/**
 * 未捕获异常测试;
 * 
 * @author crazyjohn
 *
 */
public class UnCaughtHandlerTest {
	/**
	 * 抛出异常未捕获;
	 * 
	 * @author crazyjohn
	 *
	 */
	static class ThrowException implements Runnable {
		@Override
		public void run() {
			while (true) {
				throw new RuntimeException("ThrowException");
			}
		}

	}

	/**
	 * 捕获异常;
	 * 
	 * @author crazyjohn
	 *
	 */
	static class CatchException implements Runnable {

		@Override
		public void run() {
			try {
				throw new RuntimeException("ThrowException");
			} catch (Exception e) {
				System.out.println(Thread.currentThread() + " CatchException");
			}
		}

	}

	/**
	 * set exceptionHandler
	 * 
	 * @author crazyjohn
	 *
	 */
	static class StrongThread extends Thread {
		public StrongThread(Runnable task) {
			super(task);
			this.setName("StrongThread");
			this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println(t.getName() + ": " + e);
				}
			});
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new CatchException());
		t1.setName("CatchExceptionThread");
		t1.start();
		try {
			Thread t2 = new Thread(new ThrowException());
			t2.setName("ThrowExceptionThread");
			t2.start();
			Thread strongThread = new StrongThread(new ThrowException());
			strongThread.start();
		} catch (Exception e) {
			System.out.println("Main catch exeption: " + e);
		}
	}

}
