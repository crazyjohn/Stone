package com.stone.core.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.stone.core.msg.IMessage;
import com.stone.core.msg.ProtobufMessage;

/**
 * 游戏解码器;
 * 
 * @author crazyjohn
 *
 */
public class GameDecoder implements ProtocolDecoder {
	private IoBuffer readBuffer = IoBuffer
			.allocate(IMessage.DECODE_MESSAGE_LENGTH);

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		// decode
		in.flip();
		readBuffer.put(in);
		while (true) {
			// 是否足够消息头的长度?
			if (readBuffer.remaining() < IMessage.HEADER_SIZE) {
				return;
			}
			// 读出消息包的长度
			short messageLength = readBuffer.getShort(2);
			if (readBuffer.remaining() < messageLength) {
				return;
			}
			// 读出消息包
			byte[] datas = new byte[messageLength];
			readBuffer.flip();
			readBuffer.get(datas);
			IoBuffer aMessageBuffer = IoBuffer.wrap(datas);
			ProtobufMessage protobufMessage = new ProtobufMessage();
			protobufMessage.setBuffer(aMessageBuffer);
			protobufMessage.read();
			out.write(protobufMessage);
			// 调整
			readBuffer.compact();
		}

	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		// do nothing

	}

	@Override
	public void dispose(IoSession session) throws Exception {
		this.readBuffer.free();
	}

}
