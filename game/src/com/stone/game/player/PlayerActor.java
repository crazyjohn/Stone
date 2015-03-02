package com.stone.game.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.db.msg.system.SystemLoginResult;
import com.stone.game.msg.ProtobufMessage;

/**
 * The palyer actor;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends UntypedActor {
	/** real player */
	protected final Player player;
	protected ActorRef dbMaster;
	private Logger logger = LoggerFactory.getLogger(PlayerActor.class);

	public PlayerActor(Player player) {
		this.player = player;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			// net message use self execute
			ProtobufMessage netMessage = (ProtobufMessage) msg;
			netMessage.execute();
		} else if (msg instanceof SystemLoginResult) {
			SystemLoginResult loginResult = (SystemLoginResult) msg;
			if (loginResult.getPlayerEntities().size() > 0) {
				logger.info(String.format("Player login, userName: %s", loginResult.getPlayerEntities().get(0).getUserName()));
			}
		} else {
			unhandled(msg);
		}
	}

	public static Props props() {
		return Props.create(PlayerActor.class, new Player());
	}

}
