package com.stone.gate.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class GateClientSession extends BaseSession {

	public GateClientSession(IoSession session) {
		super(session);
	}

}
