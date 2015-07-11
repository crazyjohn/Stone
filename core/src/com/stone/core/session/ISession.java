package com.stone.core.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.IMessage;

/**
 * The session;
 * 
 * @author crazyjohn
 *
 */
public interface ISession {
	/**
	 * Is this session already connected?
	 * 
	 * @return
	 */
	public boolean isConnected();

	/**
	 * Close the session;
	 */
	public void close();

	/**
	 * Write message;
	 * 
	 * @param message
	 */
	public void writeMessage(IMessage message);

	/**
	 * Get the session;
	 * 
	 * @return
	 */
	public IoSession getSession();
}
