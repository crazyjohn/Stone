package com.stone.game.handler;

import com.stone.core.annotation.Handler;
import com.stone.core.processor.MessageType;
import com.stone.game.msg.CGMessage;

/**
 * 消息处理器接口;
 * <p>
 * 1. 实现消息的自处理;<br>
 * 2. 可以和注解结合{@link Handler} 实现替代反射方案的动态反射, 或者可以直接使用ioc容器管理;
 * 
 * @author crazyjohn
 *
 */
public interface IMessageHandlerWithType {
	/**
	 * 处理指定消息;
	 * 
	 * @param msg
	 */
	public void execute(CGMessage msg);

	/**
	 * 获取处理的消息类型;
	 * 
	 * @return
	 */
	public MessageType getMessageType();
}
