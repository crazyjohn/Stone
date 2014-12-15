package com.stone.core.msg;

/**
 * 持有会话的消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface ISessionMessage extends IMessage {
	/**
	 * 获取回话信息;
	 * 
	 * @return
	 */
	public ISession getSession();
}
