package com.stone.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.session.ISession;
import com.stone.game.msg.CGMessage;
import com.stone.game.msg.GameSessionOpenMessage;
import com.stone.game.player.PlayerActor;

/**
 * The master actor;
 * 
 * @author crazyjohn
 *
 */
public class GameMaster extends UntypedActor {
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameMaster.class);
	/** dbMaster */
	private final ActorRef dbMaster;
	
	public GameMaster(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// CG消息分发
		if (msg instanceof GameSessionOpenMessage) {
			GameSessionOpenMessage sessionOpenMsg = (GameSessionOpenMessage) msg;
			if (sessionOpenMsg.getSession().getPlayerActor() == null) {
				ActorRef playerActor = getContext().actorOf(PlayerActor.props(sessionOpenMsg.getSession().getSession(), dbMaster));
				sessionOpenMsg.getSession().setPlayerActor(playerActor);
				playerActor.forward(msg, getContext());
			} else {
				// invalid, close session
				sessionOpenMsg.getSession().close();
			}
		}
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

	public static Props props(ActorRef dbMaster) {
		return Props.create(GameMaster.class, dbMaster);
	}

}
