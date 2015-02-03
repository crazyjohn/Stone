package com.stone.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	/** actor instance */
	private static GameActorSystem instance = new GameActorSystem();
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameActorSystem.class);
	/** playerActors */
	private Map<Long, Player> players = new ConcurrentHashMap<Long, Player>();

	@Override
	public void initSystem(int threadNum) {
		systemPrefix = "GameActorSystem-";
		super.initSystem(threadNum);
	}

	public static synchronized GameActorSystem getInstance() {
		return instance;
	}

	/**
	 * add player when player auth ok;
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		players.put(player.getPlayerId(), player);
	}

	/**
	 * get player;
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayer(long playerId) {
		return players.get(playerId);
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
