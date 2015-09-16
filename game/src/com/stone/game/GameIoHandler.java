package com.stone.game;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.BaseActorSession;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.game.session.msg.GameSessionOpenMessage;

/**
 * Game io handler;
 * 
 * @author crazyjohn
 *
 */
@Deprecated
public class GameIoHandler extends AbstractIoHandler<BaseActorSession> {
	/** the db master */
	protected final ActorRef dbMaster;

	public GameIoHandler(ActorRef gameMaster, ActorRef dbMaster) {
		super(gameMaster);
		this.dbMaster = dbMaster;
	}

	@Override
	protected BaseActorSession createSessionInfo(IoSession session) {
		BaseActorSession sessionInfo = new BaseActorSession(session);
		return sessionInfo;
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionCloseMessage(BaseActorSession sessionInfo) {
		return new GameSessionCloseMessage(sessionInfo);
	}

	@Override
	protected ISessionMessage<BaseActorSession> createSessionOpenMessage(BaseActorSession sessionInfo) {
		return new GameSessionOpenMessage(sessionInfo);
	}

}
