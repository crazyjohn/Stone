package com.stone.game.player;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.game.msg.ProtobufMessage;
import com.stone.proto.Auths.Login;

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

	public PlayerActor(Player player) {
		this.player = player;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			// net message use self execute
			ProtobufMessage netMessage = (ProtobufMessage) msg;
			netMessage.execute();
		}else if (msg instanceof Login) {
			
		} else {
			unhandled(msg);
		}
	}

	public static Props props() {
		return Props.create(PlayerActor.class, new Player());
	}

}
