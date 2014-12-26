package com.stone.core.codec;

import com.stone.core.msg.IMessage;

/**
 * 消息工厂;
 * 
 * @author crazyjohn
 *
 * @param <Message>
 */
public interface IMessageFactory {
	public IMessage createMessage(short type);
}
