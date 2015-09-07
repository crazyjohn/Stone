package com.stone.world;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.node.service.BaseActorSystem;
import com.stone.world.actor.WorldMaster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class WorldActorSystem extends BaseActorSystem {

	public WorldActorSystem() {
		Config config = ConfigFactory.load().getConfig("WORLD");
		this.system = ActorSystem.create(this.getClass().getSimpleName(), config);
		this.master = system.actorOf(Props.create(WorldMaster.class), "worldMaster");
	}
}
