package com.stone.gate;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.gate.session.GateInternalSession;

public class GateInternalIoHandler extends AbstractIoHandler<GateInternalSession> {

	public GateInternalIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<GateInternalSession> createSessionOpenMessage(GateInternalSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GateInternalSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<GateInternalSession> createSessionCloseMessage(GateInternalSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
