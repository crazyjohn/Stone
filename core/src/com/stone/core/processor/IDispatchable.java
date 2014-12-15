package com.stone.core.processor;

/**
 * 可分发对象的接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDispatchable {
	/**
	 * 获取处理器;
	 * 
	 * @param myDispatcher
	 * @return
	 */
	public IMessageProcessor getProcessor(IDispatcher myDispatcher);
}
