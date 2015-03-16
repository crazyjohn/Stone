package com.stone.core.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

import com.google.protobuf.Message.Builder;

/**
 * The base protobuf entity;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseProtobufEntity<B extends Builder> implements IEntity, IProtobufEntity {
	protected B builder;

	protected BaseProtobufEntity(B builder) {
		this.builder = builder;
	}

	@Override
	public B getBuilder() {
		return builder;
	}
	
	@Override
	public final void write(IoBuffer buffer) throws IOException {
		buffer.put(this.toByteArray(buffer));
	}

	@Override
	public final void read(IoBuffer buffer) throws IOException {
		builder.mergeDelimitedFrom(buffer.asInputStream());
	}

	protected byte[] toByteArray(IoBuffer buffer) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		builder.clone().build().writeDelimitedTo(baos);
		return baos.toByteArray();
	}

}
