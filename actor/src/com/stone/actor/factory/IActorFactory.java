package com.stone.actor.factory;

import com.stone.actor.manager.ManagerActor;
import com.stone.actor.player.PlayerActor;

public interface IActorFactory {

	public PlayerActor createPlayerActor(int playerId);

	public ManagerActor createManagerActor(int managerId);

}
