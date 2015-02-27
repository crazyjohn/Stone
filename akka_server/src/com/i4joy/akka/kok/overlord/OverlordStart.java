package com.i4joy.akka.kok.overlord;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class OverlordStart {
	private final ActorSystem system;

	public OverlordStart(Address joinAddress,String name) {
		Config config = ConfigFactory.load().getConfig("OVERLORD");// 读取配置 application.conf
		system = ActorSystem.create(Property.SYSTEMNAME,config);// Overlord系统
		Cluster.get(system).join(joinAddress);// 系统加入集群
		system.actorOf(Overlord.props(name));
	}
}
