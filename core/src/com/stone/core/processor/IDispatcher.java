package com.stone.core.processor;


/**
 * 分发器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IDispatcher extends IMessageProcessor {

	/**
	 * 获取处理器个数;
	 * 
	 * @return
	 */
	public int getProcessorCount();

	/**
	 * 根据处理器索引获取处理器;
	 * 
	 * @param processorIndex
	 * @return
	 */
	public IMessageProcessor getProcessor(int processorIndex);
}
