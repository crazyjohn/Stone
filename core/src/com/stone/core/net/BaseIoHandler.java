package com.stone.core.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.session.ISession;

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
		ISession sessionInfo = (ISession) session.getAttribute("sessionInfo");
		if (sessionInfo == null) {
			// 无回话信息的直接关闭
			session.close(true);
		}
		if (message instanceof ISessionMessage) {
			ISessionMessage msg = (ISessionMessage) message;
			msg.setSession(sessionInfo);
			processor.put(msg);
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
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}
}
