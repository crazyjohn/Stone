package com.stone.core.cache.jedis;

import redis.clients.jedis.Transaction;

public class JedisTransactionTest {

	public static void main(String[] args) {
		String host = "10.0.1.139";
		int port = 6379;
		IRedisClientFactory factory = new RedisFactory(host, port);
		RedisClient client = factory.createRedisClient();
		// transanction
		Transaction transaction = client.multi();
		String key = "transaction";
		transaction.hset(key, "name", "test");
		transaction.hset(key, "content", "nothing");
		transaction.exec();
		client.pipelined();
	}

}
