package com.stone.core.akka.game;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class GameApp {

	public static void main(String[] args) throws InterruptedException {
		// crate system
		ActorSystem system = ActorSystem.create("GameSystem");
		// create gamePlayer one
		GamePlayer realPlayerOne = new GamePlayer("crazyjohn");
		ActorRef playerOne = system.actorOf(GamePlayerActor.props(realPlayerOne));
		playerOne.tell(new GameMessage(), ActorRef.noSender());
		// say hi
		GamePlayer bot = new GamePlayer("bot");
		ActorRef botActor = system.actorOf(GamePlayerActor.props(bot));
		SayHi hi = new SayHi(botActor);
		playerOne.tell(hi, ActorRef.noSender());
		Thread.sleep(5 * 1000);
		system.shutdown();
	}

}
