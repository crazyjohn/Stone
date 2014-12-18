package com.stone.core.log;

/**
 * 基础日志实现;
 * 
 * @author crazyjohn
 *
 */
public class Logger implements ILogger {
	protected int level;

	public Logger(int level) {
		this.level = level;
	}

	@Override
	public void debug(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String msg, Exception e) {
		// TODO Auto-generated method stub

	}

}
