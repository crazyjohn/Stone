package com.stone.core.processor;

/**
 * 分发器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDispatcher extends IMessageProcessor {

	/**
	 * 返回处理器个数;
	 * 
	 * @return
	 */
	public int getProcessorCount();

	/**
	 * 根据索引获取处理器;
	 * 
	 * @param processorIndex
	 * @return
	 */
	public IMessageProcessor getProcessor(int processorIndex);
}
