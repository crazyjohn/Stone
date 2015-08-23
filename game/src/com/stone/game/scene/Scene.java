package com.stone.game.scene;

import akka.actor.ActorRef;
import akka.event.japi.ScanningEventBus;

import com.stone.game.scene.Scene.SceneDispatchEvent;

public class Scene extends ScanningEventBus<SceneDispatchEvent, ActorRef, Integer> {
	private final int sceneId;

	public Scene(int sceneId) {
		this.sceneId = sceneId;
	}

	public static class SceneDispatchEvent {
		private final int sceneId;

		public SceneDispatchEvent(int sceneId) {
			this.sceneId = sceneId;
		}

		public int getSceneId() {
			return this.sceneId;
		}
	}

	@Override
	public int compareClassifiers(Integer oneClassifier, Integer anotherClassifier) {
		return oneClassifier.compareTo(anotherClassifier);
	}

	@Override
	public int compareSubscribers(ActorRef onePlayer, ActorRef anotherPlayer) {
		return onePlayer.compareTo(anotherPlayer);
	}

	@Override
	public boolean matches(Integer classifier, SceneDispatchEvent event) {
		// just return true
		return event.getSceneId() == this.sceneId;
	}

	@Override
	public void publish(SceneDispatchEvent event, ActorRef playerActor) {
		playerActor.tell(event, ActorRef.noSender());
	}
}
