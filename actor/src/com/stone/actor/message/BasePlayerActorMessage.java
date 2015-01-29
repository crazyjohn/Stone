package com.stone.actor.message;

import com.stone.actor.player.PlayerActor;

public abstract class BasePlayerActorMessage implements IPlayerActorMessage {
	protected PlayerActor player;

	@Override
	public PlayerActor getPlayer() {
		return player;
	}

}
