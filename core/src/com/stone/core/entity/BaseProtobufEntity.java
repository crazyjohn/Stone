package com.stone.core.entity;

import com.google.protobuf.Message.Builder;

/**
 * The base protobuf entity;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseProtobufEntity<B extends Builder> implements IEntity, IProtobufEntity {
	protected B builder;

	@Override
	public B getBuilder() {
		return builder;
	}

}
