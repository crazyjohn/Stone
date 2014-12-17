package com.stone.core.net;

import com.stone.core.msg.ISession;

/**
 * Io事件处理器;
 * 
 * @author crazyjohn
 *
 */
public interface IoHandler {

	public void onSessionOpend(ISession session);

	public void onSesssionClosed(ISession session);

	public void onMessageReceived(ISession session, Object msg);

	public void onExceptionCaught(ISession session, Exception e);

}
