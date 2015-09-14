package com.stone.agent;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.ServerInternalMessage;
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
	protected Object doMessageWrapper(ISessionMessage<BaseActorSession> msg) {
		return new ServerInternalMessage(((ProtobufMessage) msg));
	}

	@Override
	protected BaseActorSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionCloseMessage(BaseActorSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
