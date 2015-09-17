package com.stone.agent.network;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.msg.IMessage;
import com.stone.core.msg.server.GAForwardMessage;
import com.stone.core.msg.server.ServerBetweenMessage;
import com.stone.proto.MessageTypes.MessageType;

public class AgentInternalMessageFactory implements IMessageFactory {

	@Override
	public IMessage createMessage(int type) {
		switch (type) {
		case MessageType.GAME_REGISTER_TO_AGENT_VALUE:
			return new ServerBetweenMessage(type);
		default:
			return new GAForwardMessage(type);
		}
	}

}
