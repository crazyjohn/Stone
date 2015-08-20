package com.stone.game;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.GameActorSession;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.game.session.msg.GameSessionOpenMessage;

/**
 * Game io handler;
 * 
 * @author crazyjohn
 *
 */
public class GameIoHandler extends AbstractIoHandler<GameActorSession> {
	/** the db master */
	protected final ActorRef dbMaster;

	public GameIoHandler(ActorRef gameMaster, ActorRef dbMaster) {
		super(gameMaster);
		this.dbMaster = dbMaster;
	}

	@Override
	protected GameActorSession createSessionInfo(IoSession session) {
		GameActorSession sessionInfo = new GameActorSession(session);
		return sessionInfo;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error(String.format("Exception caught, session: %s", session), cause);
	}

	@Override
	protected ISessionMessage<GameActorSession> createSessionCloseMessage(GameActorSession sessionInfo) {
		return new GameSessionCloseMessage(sessionInfo);
	}

	@Override
	protected ISessionMessage<GameActorSession> createSessionOpenMessage(GameActorSession sessionInfo) {
		return new GameSessionOpenMessage(sessionInfo);
	}

}
