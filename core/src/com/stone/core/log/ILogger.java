package com.stone.core.log;

/**
 * 日志接口;
 * 
 * @author crazyjohn
 *
 */
public interface ILogger {

	public void debug(String msg);

	public void info(String msg);

	public void warn(String msg);

	public void error(String msg, Exception e);
}
