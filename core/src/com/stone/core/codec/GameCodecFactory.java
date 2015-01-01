package com.stone.core.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 游戏编解码工厂;
 * 
 * @author crazyjohn
 *
 */
public class GameCodecFactory implements ProtocolCodecFactory {
	private IMessageFactory messageFactory;

	public GameCodecFactory(IMessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new GameEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return new GameDecoder(messageFactory);
	}

}
