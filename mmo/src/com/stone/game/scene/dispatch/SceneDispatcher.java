package com.stone.game.scene.dispatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;

import com.stone.core.concurrent.annotation.ThreadSafeUnit;
import com.stone.game.scene.Scene;

/**
 * The scene dispatcher;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class SceneDispatcher {
	private static SceneDispatcher instance = new SceneDispatcher();
	/** scene eventBuses */
	private Map<Integer, Scene> eventBuses = new ConcurrentHashMap<Integer, Scene>();

	public void registerSceneEventBus(int sceneId) {
		eventBuses.put(sceneId, new Scene(sceneId));
	}

	/**
	 * Dispatch the sceneEvent to actors;
	 * 
	 * @param event
	 */
	public void dispatchSceneEvent(SceneDispatchEvent event) {
		Scene scene = eventBuses.get(event.getSceneId());
		if (scene == null) {
			return;
		}
		scene.publish(event);
	}

	public static SceneDispatcher getInstance() {
		return instance;
	}

	
	/**
	 * Enter scene;
	 * <p>
	 * do subscribe things;
	 * 
	 * @param sceneId
	 * @param playerActor
	 */
	public void enterScene(int sceneId, ActorRef playerActor) {
		Scene scene = eventBuses.get(sceneId);
		if (scene == null) {
			return;
		}
		scene.subscribe(playerActor, sceneId);
	}

	/**
	 * Leave scene;
	 * <p>
	 * do unsubscribe things;
	 * 
	 * @param sceneId
	 * @param playerActor
	 */
	public void leaveScene(int sceneId, ActorRef playerActor) {
		Scene scene = eventBuses.get(sceneId);
		if (scene == null) {
			return;
		}
		scene.unsubscribe(playerActor, sceneId);
	}
}
