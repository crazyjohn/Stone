package com.stone.game.msg;

import java.util.ArrayList;
import java.util.List;

import com.stone.core.msg.IMessage;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.processor.QueueMessageProcessor;

/**
 * 分发器基础实现;
 * 
 * @author crazyjohn
 *
 */
public class Dispatcher implements IDispatcher {
	private List<IMessageProcessor> processors;

	public Dispatcher(int processorCount) {
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
	public void put(IMessage msg) {
		// TODO 处理分发
		// 处理CGMessage
		if (msg instanceof CGMessage) {
			IMessageProcessor processor = ((CGMessage) msg).getHuman()
					.getProcessor(this);
			if (processor != null) {
				processor.put(msg);
			}
		}
	}

	@Override
	public int getProcessorCount() {
		return processors.size();
	}

	@Override
	public IMessageProcessor getProcessor(int processorIndex) {
		return processors.get(processorIndex);
	}

}
