package com.stone.core.state;

import com.stone.core.processor.MessageType;

/**
 * 状态接口;
 * 
 * @author crazyjohn
 *
 */
public interface IState {
	/**
	 * 是否可以处理指定消息类型;
	 * 
	 * @param type
	 * @return
	 */
	public boolean canProcessMessage(MessageType type);

	/**
	 * 是否可以转换到指定的状态;
	 * 
	 * @param state
	 * @return
	 */
	public boolean canTransferTo(IState state);
}
