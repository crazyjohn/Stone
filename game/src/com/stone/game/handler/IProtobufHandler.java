package com.stone.game.handler;

import com.google.protobuf.Message.Builder;

/**
 * Protobuf实体处理接口;
 * 
 * @author crazyjohn
 * 
 * @param <Message>
 */
public interface IProtobufHandler<Message extends Builder> {
	/**
	 * 处理方法;
	 * 
	 * @param message
	 */
	public void execute(Message message);

	/**
	 * 获取处理类型;
	 * 
	 * @return
	 */
	public short getMessageType();
}
