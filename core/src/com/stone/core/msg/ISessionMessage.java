package com.stone.core.msg;

import com.stone.core.session.ISession;

/**
 * 回话消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface ISessionMessage<S extends ISession> extends IMessage {
	/**
	 * 获取绑定的回话;
	 * 
	 * @return
	 */
	public S getSession();
	
	public void setSession(S session);
}
