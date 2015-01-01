package com.stone.game;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger(GameIoHandler.class);

	public GameIoHandler(IMessageProcessor processor) {
		super(processor);
	}

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionOpenMessage(
			GamePlayerSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GamePlayerSession createSessionInfo(IoSession session) {
		GamePlayerSession sessionInfo = new GamePlayerSession(session);
		Player player = new Player();
		sessionInfo.setPlayer(player);
		return sessionInfo;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error(String.format("Exception caught, session: %s", session), cause);
	}

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionCloseMessage(
			GamePlayerSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
