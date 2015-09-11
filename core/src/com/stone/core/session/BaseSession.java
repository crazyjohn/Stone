package com.stone.core.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.IMessage;

/**
 * Base session;
 * 
 * @author crazyjohn
 *
 */
public class BaseSession implements ISession {
	/** bound session */
	protected IoSession session;

	public BaseSession(IoSession session) {
		this.session = session;
	}

	@Override
	public boolean isConnected() {
		return session.isConnected();
	}

	@Override
	public void close() {
		session.close(true);
	}

	@Override
	public void writeMessage(IMessage message) {
		if (session != null) {
			session.write(message);
		}
	}

	@Override
	public IoSession getSession() {
		return session;
	}

}
