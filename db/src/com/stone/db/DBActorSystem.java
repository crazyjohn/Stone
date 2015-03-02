package com.stone.db;

import java.util.Properties;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.db.service.IDBService;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.system.ISystem;
import com.stone.db.service.DBConfiguration;
import com.stone.db.service.DBServiceFactory;

/**
 * The db actor system;
 * 
 * @author crazyjohn
 *
 */
public class DBActorSystem implements ISystem, IMessageProcessor {
	/** actor system */
	private final ActorSystem system;
	/** db master */
	private final ActorRef dbMaster;
	private IDBService dbService;

	public DBActorSystem() {
		system = ActorSystem.create("DBActorSystem");
		// dbMaster
		dbMaster = system.actorOf(DBMaster.props(dbService));
	}

	public void initDBService(String dbServiceType, String dbConfigName, Properties props) {
		dbService = DBServiceFactory.createDBService(new DBConfiguration(dbServiceType, dbConfigName, props));
	}

	public ActorSystem system() {
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
	public void stop() {
		this.shutdown();
	}

	@Override
	public void put(IMessage msg) {
		// TODO Auto-generated method stub

	}

	public ActorRef dbMaster() {
		return dbMaster;
	}

}
