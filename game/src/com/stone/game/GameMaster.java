package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

/**
 * The master actor;
 * 
 * @author crazyjohn
 *
 */
public class GameMaster extends UntypedActor {
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameMaster.class);
	/** default login router count */
	private static final int DEFAULT_ROUTER_COUNT = 10;

	@Override
	public void onReceive(Object msg) throws Exception {
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

		} else {
			unhandled(msg);
		}
	}

	public static Props props() {
		return Props.create(GameMaster.class).withRouter(new RoundRobinRouter(DEFAULT_ROUTER_COUNT));
	}

}
