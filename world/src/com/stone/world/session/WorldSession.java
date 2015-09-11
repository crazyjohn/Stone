package com.stone.world.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;

/**
 * The world session;
 * 
 * @author crazyjohn
 *
 */
public class WorldSession extends BaseSession {

	public WorldSession(IoSession session) {
		super(session);
	}

}
