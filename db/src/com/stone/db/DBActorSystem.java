package com.stone.db;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.stone.core.msg.IMessage;
import com.stone.core.processor.IMessageProcessor;
import com.stone.core.system.ISystem;

/**
 * The db actor system;
 * 
 * @author crazyjohn
 *
 */
public class DBActorSystem implements ISystem, IMessageProcessor {
	final ActorSystem system;
	final ActorRef dbMaster;

	public DBActorSystem() {
		system = ActorSystem.create("DBActorSystem");
		// dbMaster
		dbMaster = system.actorOf(DBMaster.props());
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

}
