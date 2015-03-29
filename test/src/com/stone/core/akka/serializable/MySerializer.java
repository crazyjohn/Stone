package com.stone.core.akka.serializable;

import akka.serialization.JSerializer;

/**
 * My own serizlizer;
 * 
 * @author crazyjohn
 *
 */
public class MySerializer extends JSerializer {

	@Override
	public int identifier() {
		// pick u unique identifier for you serializer
		// you've got a couple of billions to choose from,
		// 0 - 16 is reserved by akka itself
		return 0;
	}

	@Override
	public boolean includeManifest() {
		// this is whether fromBinary requires a clazz or not
		return false;
	}

	@Override
	public byte[] toBinary(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromBinaryJava(byte[] bytes, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
