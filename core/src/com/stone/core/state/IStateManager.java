package com.stone.core.state;

/**
 * 状态管理器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IStateManager {
	/**
	 * 获取当前状态;
	 * 
	 * @return
	 */
	public IState getCurrentState();

	/**
	 * 设置当前状态;
	 * 
	 * @param state
	 * @return
	 */
	public IState setCurrentState(IState state);

	/**
	 * 是否可以转换到指定状态;
	 * 
	 * @param state
	 * @return
	 */
	public boolean canTransferStateTo(IState state);

	/**
	 * 转换到指定状态;
	 * 
	 * @param state
	 * @return
	 */
	public boolean transferStateTo(IState state);

}
