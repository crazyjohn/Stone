package com.stone.core.cache.jedis;

import redis.clients.jedis.Jedis;

public interface IRedisFactory {
	public Jedis getRedisClient();
	
	public void close();
}
