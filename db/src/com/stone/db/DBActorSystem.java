package com.stone.db;

import java.util.Properties;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.db.service.IDBService;
import com.stone.core.system.ISystem;
import com.stone.db.service.DBConfiguration;
import com.stone.db.service.DBServiceFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The db actor system;
 * 
 * @author crazyjohn
 *
 */
public class DBActorSystem implements ISystem {
	/** actor system */
	private final ActorSystem system;
	/** db master */
	private ActorRef dbMaster;
	/** dbService */
	private IDBService dbService;

	public DBActorSystem() {
		// load DB config
		Config config = ConfigFactory.load().getConfig("DB");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
	}

	public void initDBService(String dbServiceType, String dbConfigName, Properties props) {
		dbService = DBServiceFactory.createDBService(new DBConfiguration(dbServiceType, dbConfigName, props));
		// dbMaster
		dbMaster = system.actorOf(DBMaster.props(dbService), "DBMaster");
	}

	@Override
	public ActorSystem getSystem() {
		return system;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		this.system.shutdown();
	}

	@Override
	public ActorRef getMasterActor() {
		return dbMaster;
	}

}
