package com.stone.game.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.node.system.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The game message router;
 * 
 * @author crazyjohn
 *
 */
public class GameActorSystem extends BaseActorSystem {

	/**
	 * DeadLetter actor;
	 * 
	 * @author crazyjohn
	 *
	 */
	static class DeadLetterActor extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof DeadLetter) {
				logger.warn(String.format("Received deadLetter: %s", msg));
			}
		}

	}

	public GameActorSystem(ActorRef dbMaster) {
		// load GAME config
		Config config = ConfigFactory.load().getConfig("GAME");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
		master = system.actorOf(GameMaster.props(dbMaster), "gameMaster");
		// deadletter actor
		ActorRef deadLetterActor = system.actorOf(Props.create(DeadLetterActor.class));
		system.eventStream().subscribe(deadLetterActor, DeadLetter.class);
	}

}
