package com.stone.gate;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.gate.session.GateSession;

public class GateIoHandler extends AbstractIoHandler<GateSession> {

	public GateIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<GateSession> createSessionOpenMessage(GateSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GateSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<GateSession> createSessionCloseMessage(GateSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
