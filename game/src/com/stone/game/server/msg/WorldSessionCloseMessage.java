package com.stone.game.server.msg;

import com.stone.core.msg.BaseActorMessage;
import com.stone.core.msg.MessageParseException;

public class WorldSessionCloseMessage extends BaseActorMessage {

	@Override
	public void execute() throws MessageParseException {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean readBody() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean writeBody() {
		// TODO Auto-generated method stub
		return false;
	}

}
