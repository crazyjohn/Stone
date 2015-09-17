package com.stone.game.network;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.server.AGForwardMessage;

public class GameMessageFactory implements IMessageFactory {

	@Override
	public IMessage createMessage(int type) {
		switch (type) {
		default:
			return new AGForwardMessage(type);
		}
	}

}
