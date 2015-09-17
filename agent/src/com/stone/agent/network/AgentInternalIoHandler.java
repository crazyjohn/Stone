package com.stone.agent.network;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.BaseActorSession;

public class AgentInternalIoHandler extends AbstractIoHandler<BaseActorSession> {

	public AgentInternalIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionOpenMessage(BaseActorSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BaseActorSession createSessionInfo(IoSession session) {
		return new BaseActorSession(session);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionCloseMessage(BaseActorSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
