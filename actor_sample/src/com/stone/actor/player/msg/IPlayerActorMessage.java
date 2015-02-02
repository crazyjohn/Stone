package com.stone.actor.player.msg;

import com.stone.actor.message.IActorMessage;
import com.stone.actor.player.PlayerActor;

public interface IPlayerActorMessage extends IActorMessage {
	public PlayerActor getPlayer();
}
