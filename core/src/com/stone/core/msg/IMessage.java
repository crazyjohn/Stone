package com.stone.core.msg;

import com.stone.core.processor.MessageType;

/**
 * 消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface IMessage {
	/**
	 * 获取消息类型;
	 * 
	 * @return
	 */
	public MessageType getMessageType();

	/**
	 * 获取消息名称;
	 * 
	 * @return
	 */
	public String getShortName();

	/**
	 * 消息写方法;(序列化)
	 */
	public void write();

	/**
	 * 消息读方法;(反序列化)
	 */
	public void read();
}
