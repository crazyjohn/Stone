package com.stone.game;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.session.GamePlayerSession;
import com.stone.game.msg.GameSessionCloseMessage;
import com.stone.game.msg.GameSessionOpenMessage;

/**
 * Game io handler;
 * 
 * @author crazyjohn
 *
 */
public class GameIoHandler extends AbstractIoHandler<GamePlayerSession> {
	/** the db master */
	protected final ActorRef dbMaster;

	public GameIoHandler(ActorRef gameMaster, ActorRef dbMaster) {
		super(gameMaster);
		this.dbMaster = dbMaster;
	}

	@Override
	protected GamePlayerSession createSessionInfo(IoSession session) {
		GamePlayerSession sessionInfo = new GamePlayerSession(session);
		return sessionInfo;
	}

	// @Override
	// public void exceptionCaught(IoSession session, Throwable cause) throws
	// Exception {
	// logger.error(String.format("Exception caught, session: %s", session),
	// cause);
	// }

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionCloseMessage(GamePlayerSession sessionInfo) {
		return new GameSessionCloseMessage(sessionInfo);
	}

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionOpenMessage(GamePlayerSession sessionInfo) {
		return new GameSessionOpenMessage(sessionInfo);
	}

}
