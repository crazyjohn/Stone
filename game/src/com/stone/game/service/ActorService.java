package com.stone.game.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;

public class ActorService {
	/** the sceneActors */
	private static Map<Integer, ActorRef> sceneActors = new ConcurrentHashMap<Integer, ActorRef>();

	public static void registerSceneActor(int eachSceneId, ActorRef actorOf) {
		sceneActors.put(eachSceneId, actorOf);
	}

	public static ActorRef getSceneActor(int sceneId) {
		return sceneActors.get(sceneId);
	}

}
