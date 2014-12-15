package com.stone.game.handler;

import com.stone.core.annotation.Handler;
import com.stone.core.processor.MessageType;
import com.stone.game.msg.CGMessage;

/**
 * 消息处理器接口;
 * <p>
 * 1. 所有消息的逻辑处理入口，具体的逻辑可以委托给玩家制定的业务管理器去做;<br>
 * 2. 实现可以添加注解{@link Handler}
 * 然后利用ioc容器(比如spring)或者自己进行全局扫描,建立和message的映射,避免易错的手动注册;
 * 
 * 
 * @author crazyjohn
 *
 */
public interface IMessageHandlerWithType {
	/**
	 * 消息处理接口;
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
