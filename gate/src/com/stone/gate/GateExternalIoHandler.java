package com.stone.gate;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.BaseActorSession;
import com.stone.gate.msg.GateSessionCloseMessage;
import com.stone.gate.msg.GateSessionOpenMessage;

/**
 * The gate client io handler;
 * 
 * @author crazyjohn
 *
 */
public class GateExternalIoHandler extends AbstractIoHandler<BaseActorSession> {

	public GateExternalIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionOpenMessage(BaseActorSession sessionInfo) {
		return new GateSessionOpenMessage(sessionInfo);
	}

	@Override
	protected BaseActorSession createSessionInfo(IoSession session) {
		BaseActorSession sessionInfo = new BaseActorSession(session);
		return sessionInfo;
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionCloseMessage(BaseActorSession sessionInfo) {
		return new GateSessionCloseMessage(sessionInfo);
	}

}
