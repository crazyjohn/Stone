package com.i4joy.akka.kok.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.sharding.ShardingMaster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartSharding {
	public StartSharding() {
		Config config = ConfigFactory.load().getConfig("SHARDING");
		final ActorSystem system = ActorSystem.create(Property.SYSTEMNAME, config);// IO
																					// 系统
		Address address = new Address("akka.tcp", Property.SYSTEMNAME, TextProperties.getText("KINGIP"), Integer.parseInt(TextProperties.getText("KINGPORT")));
		Cluster.get(system).join(address);// IO系统 加入集群

		final ActorRef shardingMaster = system.actorOf(ShardingMaster.props());
	}
}
