package com.stone.actor.main;

import com.stone.actor.factory.ActorFactory;
import com.stone.actor.factory.IActorFactory;
import com.stone.actor.player.PlayerActor;
import com.stone.actor.system.ActorSystem;
import com.stone.actor.system.IActorSystem;

/**
 * ActorMain
 * 
 * @author crazyjohn
 * 
 */
public class ActorMain {

	public static void main(String[] args) {
		// thread num
		int threadNum = 2;
		// actor system
		IActorSystem actorSystem = ActorSystem.getInstance();
		// init
		actorSystem.initSystem(threadNum);
		// start
		actorSystem.start();
		// create a player actor
		IActorFactory factory = new ActorFactory();
		PlayerActor player = factory.createPlayerActor(1L);
		player.start();
	}

}
