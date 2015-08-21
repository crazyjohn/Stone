package com.stone.game.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;

public class SceneActorRegistry {
	/** the sceneActors */
	private static Map<Integer, ActorRef> sceneActors = new ConcurrentHashMap<Integer, ActorRef>();
	private static SceneActorRegistry instance = new SceneActorRegistry();

	private SceneActorRegistry() {
	}

	public void registerSceneActor(int eachSceneId, ActorRef actorOf) {
		sceneActors.put(eachSceneId, actorOf);
	}

	public ActorRef getSceneActor(int sceneId) {
		return sceneActors.get(sceneId);
	}

	public static SceneActorRegistry getInstance() {
		return instance;
	}

}
