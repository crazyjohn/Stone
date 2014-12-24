package com.stone.core.processor;

import java.util.ArrayList;
import java.util.List;

import com.stone.core.msg.IMessage;

/**
 * 接触分发器实现;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseDispatcher implements IDispatcher {
	private List<IMessageProcessor> processors;

	public BaseDispatcher(int processorCount) {
		processors = new ArrayList<IMessageProcessor>();
		for (int i = 0; i < processorCount; i++) {
			processors.add(new QueueMessageProcessor());
		}
	}

	@Override
	public void start() {
		for (IMessageProcessor processor : processors) {
			processor.start();
		}
	}

	@Override
	public void stop() {
		for (IMessageProcessor processor : processors) {
			processor.stop();
		}
	}

	@Override
	public abstract void put(IMessage msg);

	@Override
	public int getProcessorCount() {
		return processors.size();
	}

	@Override
	public IMessageProcessor getProcessor(int processorIndex) {
		return processors.get(processorIndex);
	}

}
