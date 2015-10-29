package com.stone.db;

import java.util.Properties;

import akka.actor.ActorSystem;

import com.stone.core.data.config.DBConfiguration;
import com.stone.core.data.config.DBServiceFactory;
import com.stone.core.db.service.IDBService;
import com.stone.core.node.system.BaseActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * The db actor system;
 * 
 * @author crazyjohn
 *
 */
public class DBActorSystem extends BaseActorSystem {
	/** dbService */
	private IDBService dbService;

	public void initDBService(String dbServiceType, String dbConfigName, Properties props) {
		dbService = DBServiceFactory.createDBService(new DBConfiguration(dbServiceType, dbConfigName, props));
		// dbMaster
		master = system.actorOf(DBMaster.props(dbService), "dBMaster");
	}

	@Override
	protected void buildActorSystem() {
		// load DB config
		Config config = ConfigFactory.load().getConfig("DB");
		system = ActorSystem.create(this.getClass().getSimpleName(), config);
	}

}
