package com.stone.db.dispatch;

import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.BaseDispatcher;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;

/**
 * DB Message Dispatcher;
 * 
 * @author crazyjohn
 *
 */
public class DBDispatcher extends BaseDispatcher {
	private IDispatcher mainDispatcher;

	public DBDispatcher(int processorCount) {
		super("DBDispatcher", processorCount);
	}

	@Override
	public void put(IMessage msg) {
		// 消息路由入队
		if (msg instanceof IDBMessage) {
			IDBMessage dbMessage = (IDBMessage) msg;
			IMessageProcessor processor = dbMessage.getProcessor(this);
			if (processor != null) {
				processor.put(dbMessage);
			} else {
				// FIXME: crazyjohn 记录无法处理的情况
			}
		}
	}

	public IDispatcher getMainDispatcher() {
		return mainDispatcher;
	}

	public void setMainDispatcher(IDispatcher mainDispatcher) {
		this.mainDispatcher = mainDispatcher;
	}

}
