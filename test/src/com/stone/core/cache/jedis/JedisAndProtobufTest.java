package com.stone.core.cache.jedis;

import java.io.UnsupportedEncodingException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.stone.proto.Humans.Human;

import redis.clients.jedis.Jedis;

/**
 * 使用Redis存储protobuf序列化以后的二进制，同时进行反序列化。
 * 
 * @author crazyjohn
 *
 */
public class JedisAndProtobufTest {

	public static void main(String[] args) throws UnsupportedEncodingException, InvalidProtocolBufferException {
		String host = "10.0.1.139";
		int port = 6379;
		String charset = "UTF-8";
		IRedisFactory factory = new RedisFactory(host, port);
		Jedis client = factory.getRedisClient();
		// set binary datas
		String puid = "crazyjohn";
		Human.Builder builder = Human.newBuilder();
		builder.setGuid(8888).setLevel(1).setName(puid).setPlayerId(6666);
		client.set(puid.getBytes(charset), builder.build().toByteArray());
		// deserializer
		byte[] humanBytes = client.get(puid.getBytes(charset));
		System.out.println(JsonFormat.printToString(Human.newBuilder().mergeFrom(humanBytes).build()));
	}

}
