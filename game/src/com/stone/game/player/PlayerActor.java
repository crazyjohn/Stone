package com.stone.game.player;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.game.msg.GameSessionCloseMessage;
import com.stone.game.msg.GameSessionOpenMessage;
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
	/** db master */
	protected final ActorRef dbMaster;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(PlayerActor.class);

	public PlayerActor(Player player, ActorRef dbMaster) {
		this.player = player;
		this.dbMaster = dbMaster;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			// net message use self execute
			ProtobufMessage netMessage = (ProtobufMessage) msg;
			player.onNetMessage(netMessage, getSelf(), dbMaster);
		} else if (msg.getClass().getAnnotation(PlayerInternalMessage.class) != null) {
			// handle player internal message
			player.onInternalMessage(msg, getSelf());
		} else if (msg instanceof GameSessionOpenMessage) {
			// open session
			GameSessionOpenMessage sessionOpen = (GameSessionOpenMessage) msg;
			sessionOpen.execute();
		} else if (msg instanceof GameSessionCloseMessage) {
			// close session
			GameSessionCloseMessage sessionClose = (GameSessionCloseMessage) msg;
			sessionClose.execute();
		} else {
			// unhandle msg
			unhandled(msg);
		}
	}

	public static Props props(IoSession session, ActorRef dbMaster) {
		Player player = new Player();
		player.setSession(session);
		// if (player.canTransferStateTo(PlayerState.CONNECTED)) {
		// player.transferStateTo(PlayerState.CONNECTED);
		// }
		return Props.create(PlayerActor.class, player, dbMaster);
	}

}
