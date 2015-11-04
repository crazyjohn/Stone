package com.stone.core.cache.jedis;

import java.io.UnsupportedEncodingException;

import redis.clients.jedis.Jedis;

/**
 * The Jedis client adapter;
 * 
 * @author crazyjohn
 *
 */
public class RedisClient extends Jedis {
	private Jedis client;

	public RedisClient(Jedis client) {
		this.client = client;
	}

	public String set(String key, byte[] value, String charset) throws UnsupportedEncodingException {
		return client.set(key.getBytes(charset), value);
	}

	public String set(String key, byte[] value) throws UnsupportedEncodingException {
		return client.set(key.getBytes("UTF-8"), value);
	}

	public byte[] getBytes(String key) throws UnsupportedEncodingException {
		return client.get(key.getBytes("UTF-8"));
	}

}
