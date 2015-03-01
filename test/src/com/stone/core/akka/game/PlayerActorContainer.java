package com.stone.core.akka.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class PlayerActorContainer implements IPlayerActorContainer {
	private Map<Long, ActorRef> actors = new ConcurrentHashMap<Long, ActorRef>();
	private AtomicLong counter = new AtomicLong();

	@Override
	public ActorRef getPlayerActorById(long playerId) {
		return actors.get(playerId);
	}

	@Override
	public ActorRef createPlayerActor(String playerName, String actorName, ActorSystem system) {
		GamePlayer player = new GamePlayer(playerName);
		ActorRef playerActor = system.actorOf(GamePlayerActor.props(player), actorName);
		actors.put(counter.incrementAndGet(), playerActor);
		return playerActor;
	}

}
