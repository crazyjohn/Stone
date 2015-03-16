package com.stone.core.entity;

import com.google.protobuf.Message.Builder;

/**
 * The protobuf entity;
 * 
 * @author crazyjohn
 *
 */
public interface IProtobufEntity {

	/**
	 * Get the builder;
	 * 
	 * @return
	 */
	public Builder getBuilder();
}
