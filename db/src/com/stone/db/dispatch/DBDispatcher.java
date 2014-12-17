package com.stone.db.dispatch;

import com.stone.core.msg.IMessage;
import com.stone.core.processor.BaseDispatcher;

/**
 * DB消息分发器;
 * 
 * @author crazyjohn
 *
 */
public class DBDispatcher extends BaseDispatcher {

	public DBDispatcher(int processorCount) {
		super(processorCount);
	}

	@Override
	public void put(IMessage msg) {
		// TODO Auto-generated method stub

	}

}
