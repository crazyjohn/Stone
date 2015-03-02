package com.stone.game;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.game.msg.GameSessionCloseMessage;
import com.stone.game.msg.GameSessionOpenMessage;
import com.stone.game.player.PlayerActor;
import com.stone.game.session.GamePlayerSession;

/**
 * Game io handler;
 * 
 * @author crazyjohn
 *
 */
public class GameIoHandler extends AbstractIoHandler<GamePlayerSession> {
	protected final ActorSystem system;

	public GameIoHandler(ActorRef processor, ActorSystem system) {
		super(processor);
		this.system = system;
	}

	@Override
	protected GamePlayerSession createSessionInfo(IoSession session) {
		ActorRef playerActor = system.actorOf(PlayerActor.props());
		GamePlayerSession sessionInfo = new GamePlayerSession(session, playerActor);
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
