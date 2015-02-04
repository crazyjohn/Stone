package com.stone.game;

import org.apache.mina.core.session.IoSession;

import com.stone.actor.system.IActorSystem;
import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.AbstractIoHandler;
import com.stone.core.processor.IMessageProcessor;
import com.stone.game.player.Player;
import com.stone.game.session.GamePlayerSession;

/**
 * 游戏世界IO处理器;
 * 
 * @author crazyjohn
 *
 */
public class GameIoHandler extends AbstractIoHandler<GamePlayerSession> {

	public GameIoHandler(IMessageProcessor processor) {
		super(processor);
	}

	@Override
	protected GamePlayerSession createSessionInfo(IoSession session) {
		GamePlayerSession sessionInfo = new GamePlayerSession(session);
		Player player = new Player();
		sessionInfo.setPlayer(player);
		// register to actor system
		if (this.processor instanceof IActorSystem) {
			IActorSystem actorSystem = (IActorSystem) this.processor;
			actorSystem.registerActor(player);
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionOpenMessage(GamePlayerSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
