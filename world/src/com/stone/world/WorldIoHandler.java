package com.stone.world;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.world.actor.WorldSession;

/**
 * The world io handler;
 * 
 * @author crazyjohn
 *
 */
public class WorldIoHandler extends AbstractIoHandler<WorldSession> {

	public WorldIoHandler(ActorRef gameMaster) {
		super(gameMaster);
	}

	@Override
	protected ISessionMessage<WorldSession> createSessionOpenMessage(WorldSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected WorldSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<WorldSession> createSessionCloseMessage(WorldSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
