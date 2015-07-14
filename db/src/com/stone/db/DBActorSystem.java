package com.stone.db;

import java.io.IOException;
import java.util.Properties;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.db.service.IDBService;
import com.stone.core.node.IStoneActorService;
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
public class DBActorSystem implements IStoneActorService {
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
		dbMaster = system.actorOf(DBMaster.props(dbService), "dBMaster");
	}

	@Override
	public ActorSystem getSystem() {
		return system;
	}


	@Override
	public void shutdown() {
		this.system.shutdown();
	}

	@Override
	public ActorRef getMasterActor() {
		return dbMaster;
	}

	@Override
	public void startup() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
