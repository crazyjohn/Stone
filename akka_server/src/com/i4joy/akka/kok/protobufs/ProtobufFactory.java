package com.i4joy.akka.kok.protobufs;

import java.nio.ByteBuffer;

import com.i4joy.akka.kok.protobufs.KOKPacket.HeartRequest;

public class ProtobufFactory {
	
	public static final int moneyNotEnough = 400;

	public static final short HEART = 1002;
	public static final short PLAYER = 1003;
	public static final short ERROR = 1004;

	public static Object getProtobuf(byte[] data) throws Exception {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		short pid = buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort();
		Object p = null;
		byte[] temp = new byte[data.length - 2];
		buffer.get(temp);
		switch (pid) {
		case HEART:
			p = HeartRequest.parseFrom(temp);
			break;
		default:
			break;
		}
		return p;
	}
}
