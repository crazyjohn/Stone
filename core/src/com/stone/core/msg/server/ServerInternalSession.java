package com.stone.core.msg.server;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class ServerInternalSession extends BaseSession {

	public ServerInternalSession(IoSession session) {
		super(session);
	}

}
