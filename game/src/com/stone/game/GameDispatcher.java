package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.BaseDispatcher;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;
import com.stone.game.player.Player;

/**
 * 游戏消息分发器;
 * 
 * @author crazyjohn
 * 
 */
public class GameDispatcher extends BaseDispatcher {
	/** db消息分发器 */
	private IDispatcher dbDispatcher;
	private Logger logger = LoggerFactory.getLogger(GameDispatcher.class);

	public GameDispatcher(int processorCount) {
		super("GameDispatcher", processorCount);
	}

	public void setDbDispatcher(IDispatcher dbDispatcher) {
		this.dbDispatcher = dbDispatcher;
	}

	@Override
	public void put(IMessage msg) {
		// TODO 分发逻辑
		// 数据消息分发
		if (msg instanceof IDBMessage) {
			dbDispatcher.put(msg);
		}
		// CG消息分发
		if (msg instanceof CGMessage) {

			Player player = ((CGMessage) msg).getPlayer();
			if (player == null) {
				ISession sessionInfo = ((CGMessage) msg).getSession();
				logger.info(String.format(
						"Player null, close this session: %s", sessionInfo));
				sessionInfo.close();
				return;
			}
			IMessageProcessor processor = ((CGMessage) msg).getPlayer()
					.getProcessor(this);
			if (processor == null) {
				logger.info(String.format("Processor null, playerId: %d",
						player.getPlayerId()));
				return;
			}
			processor.put(msg);
		}
	}
}
