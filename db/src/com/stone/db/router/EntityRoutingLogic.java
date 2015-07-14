package com.stone.db.router;


import scala.collection.immutable.IndexedSeq;
import akka.routing.Routee;
import akka.routing.RoutingLogic;

import com.stone.core.data.msg.IDBMessage;

/**
 * Entity routing logic;
 * 
 * @author crazyjohn
 *
 */
public class EntityRoutingLogic implements RoutingLogic {
	protected final int routeeCount;

	public EntityRoutingLogic(int routeeCount) {
		this.routeeCount = routeeCount;
	}

	@Override
	public Routee select(Object msg, IndexedSeq<Routee> routees) {
		if (msg instanceof IDBMessage) {
			// FIXME: crazyjohn do router logic
			
		}
		return null;
	}
}
