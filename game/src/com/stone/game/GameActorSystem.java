package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.core.msg.IMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;

/**
 * game actor system;
 * 
 * @author crazyjohn
 *
 */
public class GameActorSystem implements IMessageProcessor {
	/** actor instance */
	private static GameActorSystem instance = new GameActorSystem();
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameActorSystem.class);

	public static synchronized GameActorSystem getInstance() {
		return instance;
	}
	
	public void initSystem() {
		
	}

	@Override
	public void put(IMessage msg) {
		// CG消息分发
		if (msg instanceof CGMessage) {
			ActorRef playerActor = ((CGMessage) msg).getPlayerActor();
			if (playerActor == null) {
				ISession sessionInfo = ((CGMessage) msg).getSession();
				logger.info(String.format("Player null, close this session: %s", sessionInfo));
				sessionInfo.close();
				return;
			}
			// put to player actor
			playerActor.tell(msg, ActorRef.noSender());

		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
