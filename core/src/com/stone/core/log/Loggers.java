package com.stone.core.log;

/**
 * 静态工厂;
 * 
 * @author crazyjohn
 *
 */
public class Loggers {
	private static LoggerFactory factory = new LoggerFactory();

	public static ILogger getLogger(String loggerName) {
		return factory.getLogger(loggerName);
	}

	public static ILogger getLogger(Class<?> classType) {
		return factory.getLogger(classType);
	}
}
