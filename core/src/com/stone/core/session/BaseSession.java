package com.stone.core.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.IMessage;

/**
 * 基础会话实现;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseSession implements ISession {
	/** 绑定的回话 */
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
