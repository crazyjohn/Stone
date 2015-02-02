package com.stone.actor.factory;

import com.stone.actor.manager.ManagerActor;
import com.stone.actor.player.PlayerActor;

/**
 * the actor factory;
 * 
 * @author crazyjohn
 *
 */
public interface IActorFactory {

	/**
	 * create player actor;
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerActor createPlayerActor(long playerId);

	/**
	 * create global manager actor;
	 * 
	 * @param managerId
	 * @return
	 */
	public ManagerActor createManagerActor(long managerId);

}
