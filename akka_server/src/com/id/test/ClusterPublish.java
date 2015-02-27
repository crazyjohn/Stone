package com.id.test;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClusterPublish {

	public static void main(String args[]) throws InterruptedException {
		 Config conf = ConfigFactory.parseString("akka.cluster.roles=[" + "111" + "]").
			      withFallback(ConfigFactory.load());
		ActorSystem system = ActorSystem.create("cluster",conf);
		Address address = Cluster.get(system).selfAddress();
		Cluster.get(system).join(address);
		System.out.println(address.protocol());
		System.out.println(address.system());
		System.out.println(address.hostPort());
		system.actorOf(Props.create(PublishNode.class), "PublishNode");
//		 Thread.sleep(5000);
////		system.actorOf(Props.create(Publisher.class), "Publisher");
//		ActorSystem system2 = ActorSystem.create("cluster");
//		Cluster.get(system2).join(address);
//		 Thread.sleep(5000);
//		system2.actorOf(Props.create(Publisher.class), "Publisher");
	}
}
