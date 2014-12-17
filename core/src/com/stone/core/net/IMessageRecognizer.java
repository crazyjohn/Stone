package com.stone.core.net;

import com.stone.core.msg.IMessage;

/**
 * 消息识别器;
 * @author crazyjohn
 *
 */
public interface IMessageRecognizer {
	public IMessage recognize(IByteBuffer buffer);
}
