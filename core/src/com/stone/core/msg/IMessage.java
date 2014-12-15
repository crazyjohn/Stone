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
	 * 写自己到流;
	 * <p>
	 * 序列化方法;
	 */
	public void write();

	/**
	 * 从流中读出自己;
	 * <p>
	 * 反序列化;
	 */
	public void read();
}
