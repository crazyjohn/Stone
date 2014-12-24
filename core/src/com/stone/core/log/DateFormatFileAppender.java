package com.stone.core.log;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.RollingFileAppender;

/**
 * 根据时间日期对文件命名的日志追加器
 * 
 * @author crazyjohn
 *
 */
public class DateFormatFileAppender extends RollingFileAppender {
	@Override
	public void setFile(String file) {
		String formatedFile = MessageFormat.format(file, new Date());
		super.setFile(formatedFile);
	}
}
