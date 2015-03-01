package com.stone.core.akka.game;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public interface IPlayerActorContainer {
	
	public ActorRef getPlayerActorById(long playerId);

	public ActorRef createPlayerActor(String playerName, String actorName, ActorSystem system);
}
