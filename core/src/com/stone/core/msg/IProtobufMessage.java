package com.stone.core.msg;

import com.google.protobuf.Message.Builder;

/**
 * protobuf实体;
 * 
 * @author crazyjohn
 *
 * @param <B>
 */
public interface IProtobufMessage extends IMessage {
	

	public void setBuilder(Builder builder);

	public <B extends Builder>B parseBuilder(B newBuilder) throws MessageParseException;
}
