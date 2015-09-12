package com.stone.core.node.slave;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;

public class SlaveIoHandler extends AbstractIoHandler<MasterSession> {

	public SlaveIoHandler(ActorRef mainMasterActor) {
		super(mainMasterActor);
	}

	@Override
	protected ISessionMessage<MasterSession> createSessionOpenMessage(MasterSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MasterSession createSessionInfo(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<MasterSession> createSessionCloseMessage(MasterSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
