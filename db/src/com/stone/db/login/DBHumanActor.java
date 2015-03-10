package com.stone.db.login;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;
import com.stone.core.util.LRUHashMap;
import com.stone.db.entity.HumanEntity;
import com.stone.db.msg.internal.DBGetMessage;
import com.stone.db.msg.internal.player.InternalSelectRoleResult;

public class DBHumanActor extends UntypedActor {
	/** human cache */
	private final LRUHashMap<Long, HumanEntity> cache;
	/** dbService */
	protected final IDBService dbService;

	public DBHumanActor(int cacheSize, IDBService dbService) {
		cache = new LRUHashMap<Long, HumanEntity>(cacheSize, null);
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBGetMessage) {
			DBGetMessage getMsg = (DBGetMessage) msg;
			HumanEntity humanEntity = this.cache.get(getMsg.getId());
			if (humanEntity == null) {
				// load from db
				IEntity<?> entity = this.dbService.get(HumanEntity.class, getMsg.getId());
				if (entity == null) {
					return;
				}
				humanEntity = (HumanEntity) entity;
				getSender().tell(new InternalSelectRoleResult(humanEntity), getSelf());
			}
		}
	}
	
	public static Props props(int cacheSize, IDBService dbService) {
		return Props.create(DBHumanActor.class, cacheSize, dbService);
	}

}
