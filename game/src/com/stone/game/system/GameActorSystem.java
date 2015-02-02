package com.stone.game.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.system.ActorSystem;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;
import com.stone.game.player.Player;

/**
 * game actor system;
 * 
 * @author crazyjohn
 *
 */
public class GameActorSystem extends ActorSystem implements IMessageProcessor {
	private static GameActorSystem instance = new GameActorSystem();
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameActorSystem.class);

	public static synchronized GameActorSystem getInstance() {
		return instance;
	}

	@Override
	public void put(IMessage msg) {
		// CG消息分发
		if (msg instanceof CGMessage) {
			Player player = ((CGMessage) msg).getPlayer();
			if (player == null) {
				ISession sessionInfo = ((CGMessage) msg).getSession();
				logger.info(String.format("Player null, close this session: %s", sessionInfo));
				sessionInfo.close();
				return;
			}
			// put to player actor
			player.submit(msg);

		}
	}

}
