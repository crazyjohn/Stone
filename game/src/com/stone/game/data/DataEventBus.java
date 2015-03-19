package com.stone.game.data;

import akka.actor.ActorRef;

import com.stone.core.entity.IEntity;
import com.stone.db.msg.internal.DBDeleteMessage;
import com.stone.db.msg.internal.DBInsertMessage;
import com.stone.db.msg.internal.DBUpdateMessage;

/**
 * The db event bus;
 * <p>
 * Game business logic use this eventBus to fire the data change event, this
 * will notified the data layer to update to db;
 * 
 * @author crazyjohn
 *
 */
public class DataEventBus {

	/**
	 * Fire update;
	 * 
	 * @param dbMaster
	 * @param sender
	 * @param entity
	 */
	public static void fireUpdate(ActorRef dbMaster, ActorRef sender, IEntity entity) {
		DBUpdateMessage updateMsg = new DBUpdateMessage(entity);
		dbMaster.tell(updateMsg, sender);
	}

	/**
	 * Fire delete;
	 * 
	 * @param dbMaster
	 * @param sender
	 * @param entity
	 */
	public static void fireDelete(ActorRef dbMaster, ActorRef sender, IEntity entity) {
		DBDeleteMessage deleteMsg = new DBDeleteMessage(entity);
		dbMaster.tell(deleteMsg, sender);
	}

	/**
	 * Fire insert;
	 * 
	 * @param dbMaster
	 * @param sender
	 * @param entity
	 */
	public static void fireInsert(ActorRef dbMaster, ActorRef sender, IEntity entity) {
		DBInsertMessage insertMsg = new DBInsertMessage(entity);
		dbMaster.tell(insertMsg, sender);
	}

}
