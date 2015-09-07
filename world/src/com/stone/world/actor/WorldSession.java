package com.stone.world.actor;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

public class WorldSession extends BaseSession {

	public WorldSession(IoSession session) {
		super(session);
	}

}
