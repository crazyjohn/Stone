package com.stone.core.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.stone.core.msg.IMessage;
import com.stone.core.msg.IProtobufMessage;

/**
 * The game encoder;
 * 
 * @author crazyjohn
 *
 */
public class GameEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		IMessage msg = (IMessage) message;
		if (msg instanceof IProtobufMessage) {
			IoBuffer writeBuffer = IoBuffer.allocate(IMessage.ENCODE_MESSAGE_LENGTH);
			msg.setBuffer(writeBuffer);
			msg.write();
			int length = writeBuffer.position();
			byte[] datas = new byte[length];
			writeBuffer.flip();
			writeBuffer.get(datas);
			IoBuffer messageBuffer = IoBuffer.wrap(datas);
			out.write(messageBuffer);
		}
		// out.write(encodedMessage);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// do nothing
	}

}
