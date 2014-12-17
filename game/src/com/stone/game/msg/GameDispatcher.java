package com.stone.game.msg;

import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.BaseDispatcher;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;

/**
 * 游戏消息分发器;
 * 
 * @author crazyjohn
 *
 */
public class GameDispatcher extends BaseDispatcher {
	/** db消息分发器 */
	private IDispatcher dbDispatcher;

	public GameDispatcher(int processorCount) {
		super(processorCount);
	}

	public void setDbDispatcher(IDispatcher dbDispatcher) {
		this.dbDispatcher = dbDispatcher;
	}

	@Override
	public void put(IMessage msg) {
		// TODO 分发逻辑
		// 数据消息分发
		if (msg instanceof IDBMessage) {
			dbDispatcher.put(msg);
		}
		// CG消息分发
		if (msg instanceof CGMessage) {
			IMessageProcessor processor = ((CGMessage) msg).getHuman()
					.getProcessor(this);
			if (processor != null) {
				processor.put(msg);
			}
		}
	}

}
