package com.stone.core.entity;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

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

	/**
	 * 从数据缓存中反序列化出实体;
	 * 
	 * @param buffer
	 *            数据缓存
	 * @throws IOException
	 *             读取时候的IO异常
	 */
	public void read(IoBuffer buffer) throws IOException;

	/**
	 * 把实体序列化到数据缓存中;
	 * 
	 * @param buffer
	 *            数据缓存;
	 * @throws IOException
	 *             读取时候的IO异常
	 */
	public void write(IoBuffer buffer) throws IOException;
}
