package com.stone.gate;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.GameActorSession;

/**
 * The gate client io handler;
 * 
 * @author crazyjohn
 *
 */
public class GateExternalIoHandler extends AbstractIoHandler<GameActorSession> {

	public GateExternalIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<GameActorSession> createSessionOpenMessage(GameActorSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GameActorSession createSessionInfo(IoSession session) {
		GameActorSession sessionInfo = new GameActorSession(session);
		return sessionInfo;
	}

	@Override
	protected ISessionMessage<GameActorSession> createSessionCloseMessage(GameActorSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
