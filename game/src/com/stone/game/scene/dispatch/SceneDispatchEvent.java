package com.stone.game.scene.dispatch;

import com.google.protobuf.Message.Builder;

public class SceneDispatchEvent {
	private final int sceneId;
	private final Builder builder;

	public SceneDispatchEvent(int sceneId, Builder builder) {
		this.sceneId = sceneId;
		this.builder = builder;
	}

	public int getSceneId() {
		return this.sceneId;
	}

	public Builder getBuilder() {
		return builder;
	}
}

