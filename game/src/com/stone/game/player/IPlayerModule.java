package com.stone.game.player;

import akka.actor.ActorRef;

public interface IPlayerModule {
	
	public Player getPlayer();

	public void onMessage(Object message, ActorRef playerActor);
}
