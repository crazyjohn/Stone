package com.stone.core.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.session.ISession;

/**
 * 基础的io处理器;
 * 
 * @author crazyjohn
 *
 */
public abstract class AbstractIoHandler<S extends ISession> extends IoHandlerAdapter {
	protected ActorRef processor;
	private static final String SESSION_INFO = "SESSION_INFO";
	protected Logger logger = LoggerFactory.getLogger(AbstractIoHandler.class);

	public AbstractIoHandler(ActorRef processor) {
		this.processor = processor;
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		@SuppressWarnings("unchecked")
		S sessionInfo = (S) session.getAttribute(SESSION_INFO);
		if (sessionInfo == null) {
			// 无回话信息的直接关闭
			session.close(true);
		}
		if (message instanceof ISessionMessage) {
			@SuppressWarnings("unchecked")
			ISessionMessage<S> msg = (ISessionMessage<S>) message;
			msg.setSession(sessionInfo);
			processor.tell(msg, ActorRef.noSender());
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		@SuppressWarnings("unchecked")
		S sessionInfo = (S) session.getAttribute(SESSION_INFO);
		if (sessionInfo != null) {
			session.setAttribute(SESSION_INFO, null);
		}
		ISessionMessage<S> sessionCloseMessage = createSessionCloseMessage(sessionInfo);
		if (sessionCloseMessage == null) {
			return;
		}
		this.processor.tell(sessionCloseMessage, ActorRef.noSender());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		S sessionInfo = createSessionInfo(session);
		session.setAttributeIfAbsent(SESSION_INFO, sessionInfo);
		ISessionMessage<S> sessionOpenMessage = createSessionOpenMessage(sessionInfo);
		if (sessionOpenMessage == null) {
			return;
		}
		this.processor.tell(sessionOpenMessage, ActorRef.noSender());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// close session
		session.close(true);
	}

	/**
	 * 创建回话打开消息;
	 * 
	 * @param sessionInfo
	 * @return
	 */
	protected abstract ISessionMessage<S> createSessionOpenMessage(S sessionInfo);

	/**
	 * 创建回话信息;
	 * 
	 * @param session
	 * @return
	 */
	protected abstract S createSessionInfo(IoSession session);

	/**
	 * 创建回话关闭消息;
	 * 
	 * @param sessionInfo
	 * @return
	 */
	protected abstract ISessionMessage<S> createSessionCloseMessage(S sessionInfo);

}
