package com.stone.core.processor;

/**
 * 可分发的对象接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDispatchable {
	/**
	 * 返回自己的分发器;
	 * 
	 * @param myDispatcher
	 * @return
	 */
	public IMessageProcessor getProcessor(IDispatcher myDispatcher);
}
