package com.stone.core.cache.jedis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * The jedis cluster test;
 * 
 * @author crazyjohn
 *
 */
public class JedisClusterTest {

	public static void main(String[] args) {
		// cluster
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		HostAndPort node0 = new HostAndPort("10.0.1.139", 7000);
		HostAndPort node1 = new HostAndPort("10.0.1.139", 7001);
		HostAndPort node2 = new HostAndPort("10.0.1.139", 7002);
		HostAndPort node3 = new HostAndPort("10.0.1.139", 7003);
		HostAndPort node4 = new HostAndPort("10.0.1.139", 7004);
		HostAndPort node5 = new HostAndPort("10.0.1.139", 7005);
		nodes.add(node0);
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodes.add(node5);
		int timeout = 1000;
		int maxRediections = 100;
		JedisCluster cluster = new JedisCluster(nodes, timeout, maxRediections);
		System.out.println(cluster.hset("crazyjohn", "sex", "male"));
		System.out.println(cluster.get("hello"));
		cluster.close();
	}

}
