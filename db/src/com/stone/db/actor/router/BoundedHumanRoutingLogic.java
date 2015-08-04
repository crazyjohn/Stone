package com.stone.db.actor.router;

import scala.collection.immutable.IndexedSeq;
import akka.routing.Routee;
import akka.routing.RoutingLogic;

import com.stone.core.data.msg.IDBEntityMessage;
import com.stone.core.data.msg.IDBMessage;
import com.stone.core.entity.IHumanSubEntity;
import com.stone.db.entity.HumanEntity;

public class BoundedHumanRoutingLogic implements RoutingLogic {

	@Override
	public Routee select(Object msg, IndexedSeq<Routee> routees) {
		// must be db message
		if (!(msg instanceof IDBMessage)) {
			return null;
		}
		IDBMessage dbMsg = (IDBMessage) msg;
		Class<?> entityClass = dbMsg.getEntityClass();
		// get id
		long id = -1l;
		if (entityClass.equals(HumanEntity.class)) {
			// cast to long, bad smell
			id = (long) dbMsg.getId();
		} else if (IHumanSubEntity.class.isAssignableFrom(entityClass)
				&& (msg instanceof IDBEntityMessage)) {
			IDBEntityMessage entityMsg = (IDBEntityMessage) msg;
			IHumanSubEntity subEntity = (IHumanSubEntity) entityMsg.getEntity();
			id = subEntity.getHumanGuid();
		} else {
			return null;
		}
		Routee routee = routees.apply((int)(id % routees.size()));
		return routee;
	}

}
