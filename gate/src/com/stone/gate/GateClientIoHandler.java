package com.stone.gate;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.gate.session.GateClientSession;

/**
 * The gate client io handler;
 * 
 * @author crazyjohn
 *
 */
public class GateClientIoHandler extends AbstractIoHandler<GateClientSession> {

	public GateClientIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<GateClientSession> createSessionOpenMessage(GateClientSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GateClientSession createSessionInfo(IoSession session) {
		return new GateClientSession(session);
	}

	@Override
	protected ISessionMessage<GateClientSession> createSessionCloseMessage(GateClientSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
