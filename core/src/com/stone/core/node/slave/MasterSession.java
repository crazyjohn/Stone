package com.stone.core.node.slave;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class MasterSession extends BaseSession {

	public MasterSession(IoSession session) {
		super(session);
	}

}
