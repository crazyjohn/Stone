package com.stone.core.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础的日志工厂;
 * 
 * @author crazyjohn
 *
 */
public class LoggerFactory implements ILoggerFactory {
	protected Map<String, ILogger> loggers = new HashMap<String, ILogger>();
	protected int level;
	@Override
	public ILogger getLogger(String loggerName) {
		ILogger logger = loggers.get(loggerName);
		if (logger == null) {
			logger = createLogger(level);
			loggers.put(loggerName, logger);
		}
		return logger;
	}

	private ILogger createLogger(int level) {
		return new Logger(level);
	}

	@Override
	public ILogger getLogger(Class<?> classType) {
		return getLogger(classType.getSimpleName());
	}

}
