package com.stone.core.log;

/**
 * 日志工厂接口;
 * 
 * @author crazyjohn
 *
 */
public interface ILoggerFactory {

	public ILogger getLogger(String loggerName);

	public ILogger getLogger(Class<?> classType);
}
