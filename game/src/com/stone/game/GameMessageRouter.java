package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.msg.IMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;

/**
 * The game message router;
 * 
 * @author crazyjohn
 *
 */
public class GameMessageRouter implements IMessageProcessor {
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameMessageRouter.class);
	/** ActorSystem */
	private ActorSystem system;

	public void initSystem() {
		system = ActorSystem.create("GameActorSystem");
	}

	public ActorSystem system() {
		return system;
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
		this.system.shutdown();
	}

}
