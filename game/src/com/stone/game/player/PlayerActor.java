package com.stone.game.player;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.game.msg.ProtobufMessage;

/**
 * The palyer actor;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends UntypedActor {
	protected final Player player;

	public PlayerActor(Player player) {
		this.player = player;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			ProtobufMessage netMessage = (ProtobufMessage) msg;
			netMessage.execute();
		} else {
			unhandled(msg);
		}
	}

	public static Props props() {
		return Props.create(PlayerActor.class, new Player());
	}

}
