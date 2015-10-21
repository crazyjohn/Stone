package com.stone.agent.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class AgentClientSession extends BaseSession {

	public AgentClientSession(IoSession session) {
		super(session);
	}

}
