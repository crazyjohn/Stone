package com.i4joy.akka.kok.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.db.DBManager;
import com.i4joy.akka.kok.protocol.PCreateDBDateSource;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartDB {
	public StartDB() {
		Config config = ConfigFactory.load().getConfig("DB");
		final ActorSystem system = ActorSystem.create(Property.SYSTEMNAME, config);// IO
																					// 系统
		Address address = new Address("akka.tcp", Property.SYSTEMNAME, TextProperties.getText("KINGIP"), Integer.parseInt(TextProperties.getText("KINGPORT")));
		Cluster.get(system).join(address);// IO系统 加入集群
		
		final ActorRef dbManager = system.actorOf(DBManager.props());
		PCreateDBDateSource pdbds_user = new PCreateDBDateSource(TextProperties.getText("DB_USER"), TextProperties.getText("DB_PASSWORD"),
				TextProperties.getText("DB_ADDRESS") + TextProperties.getText("DB_NAME_USER") + "?rewriteBatchedStatements=true", TextProperties.getText("DB_DRIVER"),
				Integer.parseInt(TextProperties.getText("DB_INITPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MINPOOLSIZE")),
				Integer.parseInt(TextProperties.getText("DB_MAXPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MAXSTATEMENTS")),
				Integer.parseInt(TextProperties.getText("DB_MAXIDLETIME")), TextProperties.getText("DB_NAME_USER"));
		dbManager.tell(pdbds_user, null);
		PCreateDBDateSource pdbds_config = new PCreateDBDateSource(TextProperties.getText("DB_USER"), TextProperties.getText("DB_PASSWORD"),
				TextProperties.getText("DB_ADDRESS") + TextProperties.getText("DB_NAME_CONFIG") + "?rewriteBatchedStatements=true", TextProperties.getText("DB_DRIVER"),
				Integer.parseInt(TextProperties.getText("DB_INITPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MINPOOLSIZE")),
				Integer.parseInt(TextProperties.getText("DB_MAXPOOLSIZE")), Integer.parseInt(TextProperties.getText("DB_MAXSTATEMENTS")),
				Integer.parseInt(TextProperties.getText("DB_MAXIDLETIME")), TextProperties.getText("DB_NAME_CONFIG"));
		dbManager.tell(pdbds_config, null);
	}

	public static void main(String[] args) {
		new StartDB();
	}
}
