package com.stone.core.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.stone.core.msg.IMessage;
import com.stone.core.msg.IProtobufMessage;

/**
 * 游戏编码器;
 * 
 * @author crazyjohn
 *
 */
public class GameEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		IMessage msg = (IMessage) message;
		if (msg instanceof IProtobufMessage) {
			
		}
		//out.write(encodedMessage);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub
	}

}
