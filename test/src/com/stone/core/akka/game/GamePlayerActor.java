package com.stone.core.akka.game;

import com.stone.game.msg.ProtobufMessage;

import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * The player actor;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayerActor extends UntypedActor {
	private final GamePlayer player;

	public GamePlayerActor(GamePlayer player) {
		this.player = player;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		// handle message
		if (message instanceof ProtobufMessage) {
			// handle protobuf message
		} else if (message instanceof GameMessage) {
			player.doAnyFuckingThings();
		} else if (message instanceof SayHi) {
			SayHi hi = (SayHi) message;
			hi.getTarget().tell("Hi", getSelf());
		} else if (message instanceof String) {
			System.out.println(message);
		}

	}

	public static Props props(GamePlayer player) {
		return Props.create(GamePlayerActor.class, player);
	}

}
