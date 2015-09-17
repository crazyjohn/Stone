package com.stone.core.codec;

import com.stone.core.msg.IMessage;

/**
 * The message factory;
 * 
 * @author crazyjohn
 *
 */
public interface IMessageFactory {

	/**
	 * Create the message by type;
	 * 
	 * @param type
	 * @return
	 */
	public IMessage createMessage(int type);
}
