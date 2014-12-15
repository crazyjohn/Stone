package com.stone.core.processor;

import com.stone.core.msg.IMessage;

/**
 * 消息处理器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IMessageProcessor {
	/**
	 * 启动处理器;
	 */
	public void start();

	/**
	 * 关闭处理器;
	 */
	public void stop();

	/**
	 * 投递消息;
	 * 
	 * @param msg
	 */
	public void put(IMessage msg);
}
