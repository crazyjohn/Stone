package com.stone.game.server.msg;

import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.ServerInternalMessage;

public class AgentServerForwardMessage extends ServerInternalMessage {

	public AgentServerForwardMessage(ProtobufMessage msg) {
		super(msg);
	}

}
