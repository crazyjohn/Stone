package com.stone.gate.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class GateSession extends BaseSession {

	public GateSession(IoSession session) {
		super(session);
	}

}
