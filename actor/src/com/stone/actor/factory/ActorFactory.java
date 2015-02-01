package com.stone.actor.factory;

import com.stone.actor.manager.ManagerActor;
import com.stone.actor.player.PlayerActor;
import com.stone.actor.system.ActorSystem;
import com.stone.actor.system.IActorSystem;

/**
 * base actor factory;
 * 
 * @author crazyjohn
 *
 */
public class ActorFactory implements IActorFactory {
	private IActorSystem actorSystem = ActorSystem.getInstance();

	@Override
	public PlayerActor createPlayerActor(long playerId) {
		// create player actor
		PlayerActor player = new PlayerActor(playerId);
		actorSystem.registerActor(player);
		return player;
	}

	@Override
	public ManagerActor createManagerActor(long managerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
