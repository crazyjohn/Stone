package com.stone.db.login;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.core.util.LRUHashMap;
import com.stone.db.cache.HumanCache;
import com.stone.db.msg.internal.DBGetMessage;

public class DBHumanActor extends UntypedActor {
	/** human cache */
	private final LRUHashMap<Long, HumanCache> cache;
	/** dbService */
	protected final IDBService dbService;

	public DBHumanActor(int cacheSize, IDBService dbService) {
		cache = new LRUHashMap<Long, HumanCache>(cacheSize, null);
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBGetMessage) {
			DBGetMessage getMsg = (DBGetMessage) msg;
			HumanCache humanCache = this.cache.get(getMsg.getId());
			if (humanCache == null) {
				// FIXME: crazyjohn load from db
			}
		}
	}
	
	public static Props props(int cacheSize, IDBService dbService) {
		return Props.create(DBHumanActor.class, cacheSize, dbService);
	}

}
