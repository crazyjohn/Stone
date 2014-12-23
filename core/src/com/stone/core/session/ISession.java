package com.stone.core.session;

import com.stone.core.msg.IMessage;

/**
 * 回话接口;
 * 
 * @author crazyjohn
 *
 */
public interface ISession {
	/**
	 * 是否已经建立连接;
	 * 
	 * @return
	 */
	public boolean isConnected();

	/**
	 * 关闭会话;
	 */
	public void close();

	/**
	 * 写消息;
	 * 
	 * @param message
	 */
	public void writeMessage(IMessage message);
}
