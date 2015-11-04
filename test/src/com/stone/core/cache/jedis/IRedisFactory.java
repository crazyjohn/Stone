package com.stone.core.cache.jedis;

public interface IRedisFactory {

	public RedisClient getRedisClient();

	public void close();
}
