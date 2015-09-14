package com.stone.gate.msg;

import com.stone.core.msg.BaseCGMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.session.BaseActorSession;

public class GateSessionOpenMessage extends BaseCGMessage {

	public GateSessionOpenMessage(BaseActorSession sessionInfo) {
		this.session = sessionInfo;
	}

	@Override
	public void execute() throws MessageParseException {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean readBody() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean writeBody() {
		// TODO Auto-generated method stub
		return false;
	}

}
