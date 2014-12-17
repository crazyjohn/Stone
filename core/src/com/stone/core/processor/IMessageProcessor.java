package com.stone.core.processor;

import com.stone.core.msg.IMessage;

/**
 * 处理器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IMessageProcessor {
	/**
	 * 启动;
	 */
	public void start();

	/**
	 * 关闭;
	 */
	public void stop();

	/**
	 * 投递消息;
	 * 
	 * @param msg
	 */
	public void put(IMessage msg);
}
