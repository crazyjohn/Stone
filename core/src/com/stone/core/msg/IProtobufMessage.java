package com.stone.core.msg;

import com.google.protobuf.Message.Builder;

/**
 * protobuf实体;
 * 
 * @author crazyjohn
 *
 * @param <B>
 */
public interface IProtobufMessage<B extends Builder> extends IMessage {
	/**
	 * 获取protobuf实体;
	 * 
	 * @return
	 */
	public B getBuilder();
}
