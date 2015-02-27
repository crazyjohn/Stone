package com.i4joy.akka.kok.app;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.king.King;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartKing {
	public StartKing() {
		Config config = ConfigFactory.load();// 读取配置 application.conf
		Config conf = config.getConfig("KING");
		ActorSystem system = ActorSystem.create(Property.SYSTEMNAME, conf);// KING系统
		system.actorOf(King.getProps());
		Address realJoinAddress = Cluster.get(system).selfAddress();// 获得KING系统地址
		Cluster.get(system).join(realJoinAddress);// 系统加入集群
	}

	public static void main(String[] args) {
		new StartKing();
	}
}
