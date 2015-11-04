package com.stone.core.cache.jedis;

/**
 * The redis client factory;
 * 
 * @author crazyjohn
 *
 */
public interface IRedisClientFactory {

	/**
	 * Create the redis client;
	 * 
	 * @return
	 */
	public RedisClient createRedisClient();

	/**
	 * Close the redis client factory;
	 */
	public void close();
}
