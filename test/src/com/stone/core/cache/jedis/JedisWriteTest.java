package com.stone.core.cache.jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis写数据测试。<br>
 * 测试写100w数据用了17分钟左右, 每秒大概1000条的数据，这个效率肯定是很扯淡。但是问题应该在于过多的网络通信导致，后期做个优化看下结果。
 * 
 * <pre>
 * output:<br>
 * Begin to write data to redis: 1446546309998
 * Finish to write data to redis: 1446547308456, cost: 998458 ms
 * </pre>
 * 
 * @author crazyjohn
 *
 */
public class JedisWriteTest {

	public static void main(String[] args) {
		int botCount = 1000000;
		Random rand = new Random();
		String host = "10.0.1.139";
		int port = 6379;
		JedisPool pool = new JedisPool(host, port);
		Jedis client = pool.getResource();
		// add infos
		long begin = System.currentTimeMillis();
		System.out.println(String.format("Begin to write data to redis: %d", begin));
		for (int i = 0; i <= botCount; i++) {
			Map<String, String> props = new HashMap<String, String>();
			// props
			String name = "bot" + i;
			props.put("name", name);
			props.put("sex", "unknown");
			props.put("top", Integer.toString(rand.nextInt(200)));
			props.put("serverId", Integer.toString(rand.nextInt(1000)));
			props.put("sceneId", Integer.toString(rand.nextInt(10)));
			props.put("x", Integer.toString(rand.nextInt(1024)));
			props.put("y", Integer.toString(rand.nextInt(1024)));
			client.hmset(name, props);
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("Finish to write data to redis: %d, cost: %d ms", end, (end - begin)));
		// close pool
		pool.close();
	}

}
