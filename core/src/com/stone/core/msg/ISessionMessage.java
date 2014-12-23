package com.stone.core.msg;

import org.apache.mina.core.session.IoSession;

/**
 * 回话消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface ISessionMessage extends IMessage {
	/**
	 * 获取绑定的回话;
	 * 
	 * @return
	 */
	public IoSession getSession();
	
	public void setSession(IoSession session);
}
