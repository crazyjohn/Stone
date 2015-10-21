package com.stone.game.network;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.BaseActorSession;
import com.stone.game.server.msg.AgentSessionOpenMessage;
import com.stone.game.server.msg.AgentSesssionCloseMessage;

public class AgentIoHandler extends AbstractIoHandler<BaseActorSession> {

	public AgentIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionOpenMessage(BaseActorSession sessionInfo) {
		return new AgentSessionOpenMessage(sessionInfo);
	}

	@Override
	protected BaseActorSession createSessionInfo(IoSession session) {
		return new BaseActorSession(session);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionCloseMessage(BaseActorSession sessionInfo) {
		return new AgentSesssionCloseMessage(sessionInfo);
	}

}
