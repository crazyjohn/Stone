package com.stone.core.log;

/**
 * 基础日志实现;
 * 
 * @author crazyjohn
 *
 */
public class SimpleConsoleLogger implements ILogger {
	protected int level;

	public SimpleConsoleLogger(int level) {
		this.level = level;
	}

	@Override
	public void debug(String msg) {
		System.out.println(String.format("[DEBUG] %S", msg));
	}

	@Override
	public void info(String msg) {
		System.out.println(String.format("[INFO] %S", msg));
	}

	@Override
	public void warn(String msg) {
		System.out.println(String.format("[WARN] %S", msg));
	}

	@Override
	public void error(String msg, Exception e) {
		System.out
				.println(String.format("[ERROR] %S, %S", msg, e.getMessage()));
	}

}
