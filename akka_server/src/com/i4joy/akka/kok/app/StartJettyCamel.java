package com.i4joy.akka.kok.app;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.camel.KOKEndpointManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartJettyCamel {

	public StartJettyCamel() {
		Config config = ConfigFactory.load().getConfig("USER_SERVICE");
		final ActorSystem system = ActorSystem.create(Property.SYSTEMNAME, config);// camel system
		Address address = new Address("akka.tcp", Property.SYSTEMNAME, TextProperties.getText("KINGIP"),  Integer.parseInt(TextProperties.getText("KINGPORT")));
		Cluster.get(system).join(address);// camel系统 加入集群
		system.actorOf(Props.create(KOKEndpointManager.class), "EndpointManager");
	}

	public static void main(String[] args) {
		new StartJettyCamel();
	}
}
