package com.stone.game.scene.dispatch;


public class SceneDispatchEvent {
	private final int sceneId;
	private final Object message;

	public SceneDispatchEvent(int sceneId, Object message) {
		this.sceneId = sceneId;
		this.message = message;
	}

	public int getSceneId() {
		return this.sceneId;
	}

	public Object getMessage() {
		return message;
	}
}

