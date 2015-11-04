package com.stone.core.cache.jedis;

import java.io.UnsupportedEncodingException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.stone.proto.Humans.Human;

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
		IRedisFactory factory = new RedisFactory(host, port);
		RedisClient client = factory.getRedisClient();
		// set binary datas
		String puid = "claire";
		Human.Builder builder = Human.newBuilder();
		builder.setGuid(8888).setLevel(1).setName(puid).setPlayerId(6666);
		client.set(puid, builder.build().toByteArray());
		// deserializer
		byte[] humanBytes = client.getBytes(puid);
		System.out.println(JsonFormat.printToString(Human.newBuilder().mergeFrom(humanBytes).build()));
	}

}
