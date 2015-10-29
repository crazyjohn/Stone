package com.stone.world.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.system.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The world actor system;
 * 
 * @author crazyjohn
 *
 */
public class WorldActorSystem extends BaseActorSystem {

	@Override
	protected void buildActorSystem() {
		Config config = ConfigFactory.load().getConfig("WORLD");
		this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
		this.master = system.actorOf(Props.create(WorldMaster.class), "worldMaster");
	}

}
