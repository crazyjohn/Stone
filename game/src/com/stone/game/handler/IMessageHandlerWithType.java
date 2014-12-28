package com.stone.game.handler;

import com.stone.core.annotation.MessageHandler;
import com.stone.core.msg.IMessage;

/**
 * 消息处理器接口;
 * <p>
 * 1. 实现消息的自处理;<br>
 * 2. 可以和注解结合{@link MessageHandler} 实现替代反射方案的动态反射, 或者可以直接使用ioc容器管理;
 * 
 * @author crazyjohn
 *
 */
public interface IMessageHandlerWithType<Message extends IMessage> {
	/**
	 * 处理指定消息;
	 * 
	 * @param msg
	 */
	public void execute(Message msg);

	/**
	 * 获取处理的消息类型;
	 * 
	 * @return
	 */
	public short getMessageType();
}
