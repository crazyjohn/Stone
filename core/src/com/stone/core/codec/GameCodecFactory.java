package com.stone.core.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * The game codec factory;
 * 
 * @author crazyjohn
 *
 */
public class GameCodecFactory implements ProtocolCodecFactory {
	private static final String DECODER = "DECODER";
	private IMessageFactory messageFactory;

	public GameCodecFactory(IMessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new GameEncoder();
	}

	/**
	 * In old version, mina do this things by itself, now it's my work, so
	 * stupid.
	 * 
	 * <pre>
	 * private ProtocolDecoder getDecoder(IoSession session) throws Exception {
	 * 	ProtocolDecoder decoder = (ProtocolDecoder) session.getAttribute(DECODER);
	 * 	if (decoder == null) {
	 * 		decoder = factory.getDecoder();
	 * 		session.setAttribute(DECODER, decoder);
	 * 	}
	 * 	return decoder;
	 * }
	 * </pre>
	 */
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		ProtocolDecoder decoder = (ProtocolDecoder) session.getAttribute(DECODER);
		if (decoder == null) {
			decoder = new StupidGameDecoder(messageFactory);
			session.setAttribute(DECODER, decoder);
		}
		return decoder;
	}

}
