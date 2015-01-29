package com.stone.actor.message;

import com.stone.actor.player.PlayerActor;

public interface IPlayerActorMessage extends IActorMessage {
	public PlayerActor getPlayer();
}
