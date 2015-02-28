package com.stone.core.akka.game;

import com.stone.game.msg.ProtobufMessage;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * The player actor;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayerActor extends UntypedActor {
	private final GamePlayer player;
	LoggingAdapter logger = Logging.getLogger(this.getContext().system(), this);

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
			logger.info("Say hi to: {}", hi.getTarget());
		} else if (message instanceof String) {
			logger.info("Received String message: {}", message);
		}

	}

	public static Props props(GamePlayer player) {
		return Props.create(GamePlayerActor.class, player);
	}

}
