package com.stone.core.state;


/**
 * 状态接口;
 * 
 * @author crazyjohn
 *
 */
public interface IState {
	/**
	 * 是否可以处理指定的消息;
	 * 
	 * @param type
	 * @return
	 */
	public boolean canProcessMessage(short type);

	/**
	 * 是否可以转换到指定状态;
	 * 
	 * @param state
	 * @return
	 */
	public boolean canTransferTo(IState state);
}
