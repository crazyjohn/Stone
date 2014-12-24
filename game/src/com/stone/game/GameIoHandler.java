package com.stone.game;

import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.ISessionMessage;
import com.stone.core.net.BaseIoHandler;
import com.stone.core.processor.IMessageProcessor;
import com.stone.game.session.GamePlayerSession;

/**
 * 游戏世界IO处理器;
 * 
 * @author crazyjohn
 *
 */
public class GameIoHandler extends BaseIoHandler<GamePlayerSession> {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISessionMessage<GamePlayerSession> createSessionCloseMessage(
			GamePlayerSession sessionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}