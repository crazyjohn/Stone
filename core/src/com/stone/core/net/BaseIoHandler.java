package com.stone.core.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.processor.IMessageProcessor;

/**
 * 基础的io处理器;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseIoHandler extends IoHandlerAdapter {
	protected IMessageProcessor processor;

	public BaseIoHandler(IMessageProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof ISessionMessage) {
			processor.put((ISessionMessage) message);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}
}
